;-------------------------------------------------------------------------------
; void each_16_bytes_3x3(unsigned char* src,    | function      |      ebp+32
;                        unsigned char* dst,    | dst_row_size  |      ebp+28
;                        int width,             | src_row_size  |      ebp+24
;                        int height,            | height        |      ebp+20
;                        int src_row_size,      | width         |      ebp+16
;                        int dst_row_size,      | dst           |      ebp+12
;                        function* f);          | src           |      ebp+8
;                                               | dir ret       |      ebp+4
;                                               | EBP           |
;
;
;  3x3 kernel of a pixel:
;       | x1 x2 x3 |    x5 is the value of the pixel
;       | x4 x5 x6 |    and the other values are the
;       | x7 x8 x9 |    neighbors of that pixel
;
;  *EAX:
;        pF pE pD pC pB pA p9 p8 p7 p6 p5 p4 p3 p2 p1 p0
;   143 |x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9,x9| 128
;   127 |x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8,x8| 112
;   111 |x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7,x7| 96
;    95 |x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6,x6| 80
;    79 |x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5,x5| 64
;    63 |x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4,x4| 48
;    47 |x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3,x3| 32
;    31 |x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2,x2| 16
;    15 |x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1,x1| 0
;        pF pE pD pC pB pA p9 p8 p7 p6 p5 p4 p3 p2 p1 p0
;
;   - Loads a 3x3 kernel to array in EAX for 16 pixels
;   - Calls function
;   - Writes XMM0 into dst
;   - Does not use ECX, EDX
;-------------------------------------------------------------------------------

section .bss

    values:             resd 36

section .text

global each_16_bytes_3x3

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]
%define function        [ebp+32]

%define columns_left    [ebp-4]
%define rows_left       [ebp-8]
%define label_next      [ebp-12]
%define label_last      [ebp-16]

each_16_bytes_3x3:

    push ebp
    mov ebp, esp
    sub esp, 16
    push esi
    push edi
    push ebx

    mov dword esi, src              ; esi <- *src
    cmp dword esi, 0
    je exit

    mov dword edi, dst              ; edi <- *dst
    cmp dword edi, 0
    je exit

    mov dword ebx, height
    mov dword rows_left, ebx
    mov dword label_next, cycle_columns_row_beg_col_beg

    cycle_rows:

        cmp dword rows_left, 0
        je exit
        dec dword rows_left

        mov dword ebx, width
        mov dword columns_left, ebx
        xor ebx, ebx                            ; ebx <- row offset

        cycle_columns:

            pxor xmm0, xmm0                     ; xmm0 <- 0
            mov dword eax, values               ; eax  <- byte*[16*9]
            jmp label_next

            cycle_columns_row_beg_col_beg:

                mov dword label_next, cycle_columns_row_beg_col_mid
                mov dword label_last, cycle_columns_row_beg_col_end

                movdqu [eax], xmm0              ; save x-1
                movdqu [eax + 16], xmm0         ; save x-2
                movdqu [eax + 32], xmm0         ; save x-3

                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                pslldq xmm0, 1
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx

                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                pslldq xmm0, 1
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_beg_col_mid:

                movdqu [eax], xmm0              ; save x-1
                movdqu [eax + 16], xmm0         ; save x-2
                movdqu [eax + 32], xmm0         ; save x-3

                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx

                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_beg_col_end:

                mov dword label_next, cycle_columns_row_mid_col_beg
                mov dword label_last, cycle_columns_row_mid_col_end

                movdqu [eax], xmm0              ; save x-1
                movdqu [eax + 16], xmm0         ; save x-2
                movdqu [eax + 32], xmm0         ; save x-3

                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                psrldq xmm0, 1
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx

                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                psrldq xmm0, 1
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_mid_col_beg:

                mov dword label_next, cycle_columns_row_mid_col_mid

                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                pslldq xmm0, 1
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push dword ebx
                push dword ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                pslldq xmm0, 1
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 32], xmm0         ; save x-3

                pop ebx
                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                pslldq xmm0, 1
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_mid_col_mid:

                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx
                push ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 32], xmm0         ; save x-3

                pop dword ebx
                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_mid_col_end:

                mov dword label_next, cycle_columns_row_mid_col_beg
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                psrldq xmm0, 1
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx
                push ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                psrldq xmm0, 1
                movdqu [eax + 32], xmm0         ; save x-3

                pop dword ebx
                add dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 96], xmm0         ; save x-7
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 112], xmm0        ; save x-8
                psrldq xmm0, 1
                movdqu [eax + 128], xmm0        ; save x-9

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_end_col_beg:

                mov dword label_next, cycle_columns_row_end_col_mid
                mov dword label_last, cycle_columns_row_end_col_end

                movdqu [eax + 96], xmm0         ; save x-7
                movdqu [eax + 112], xmm0        ; save x-8
                movdqu [eax + 128], xmm0        ; save x-9

                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                pslldq xmm0, 1
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push dword ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                pslldq xmm0, 1
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 32], xmm0         ; save x-3

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_end_col_mid:

                movdqu [eax + 96], xmm0         ; save x-7
                movdqu [eax + 112], xmm0        ; save x-8
                movdqu [eax + 128], xmm0        ; save x-9

                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                movdqu xmm0, [esi + ebx + 1]
                movdqu [eax + 32], xmm0         ; save x-3

                pop ebx
                jmp cycle_column_call_function


            cycle_columns_row_end_col_end:

                movdqu [eax + 96], xmm0         ; save x-7
                movdqu [eax + 112], xmm0        ; save x-8
                movdqu [eax + 128], xmm0        ; save x-9

                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax + 48], xmm0         ; save x-4
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 64], xmm0         ; save x-5
                psrldq xmm0, 1
                movdqu [eax + 80], xmm0         ; save x-6
                push ebx

                sub dword ebx, src_row_size
                movdqu xmm0, [esi + ebx - 1]
                movdqu [eax], xmm0              ; save x-1
                movdqu xmm0, [esi + ebx]
                movdqu [eax + 16], xmm0         ; save x-2
                psrldq xmm0, 1
                movdqu [eax + 32], xmm0         ; save x-3

                pop ebx
                jmp cycle_column_call_function


        cycle_column_call_function:

            call function
            movdqu [edi + ebx], xmm0
            add ebx, 16

            sub dword columns_left, 16
            jz next_row

            cmp dword columns_left, 17
            jl last_16_pixels
            jmp cycle_columns

        last_16_pixels:

            mov dword ebx, label_last
            mov dword label_next, ebx
            mov dword ebx, width
            sub dword ebx, 16
            mov dword columns_left, 16
            jmp cycle_columns

        next_row:

            add dword esi, src_row_size
            add dword edi, dst_row_size
            cmp dword rows_left, 1
            jg cycle_rows
            mov dword label_next, cycle_columns_row_end_col_beg
            jmp cycle_rows

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 16
        pop ebp
        ret

