;-------------------------------------------------------------------------------
; void translation(unsigned char* src,      | axis_y        |       ebp+36
;                  unsigned char* dst,      | axis_x        |       ebp+32
;                  int width,               | dst_row_size  |       ebp+28
;                  int height,              | src_row_size  |       ebp+24
;                  int src_row_size,        | height        |       ebp+20
;                  int dst_row_size,        | width         |       ebp+16
;                  int axis_x,              | dst           |       ebp+12
;                  int axis_y);             | src           |       ebp+8
;                                           | dir ret       |       ebp+4
;                                           | EBP           |
;
;-------------------------------------------------------------------------------

extern image_init_xmm0

global translation

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]
%define axis_x          [ebp+32]
%define axis_y          [ebp+36]
%define fpu_var         [ebp-4]
%define cols            [ebp-8]
%define rows            [ebp-12]

translation:

    push ebp
    mov ebp, esp
    sub esp, 12
    push esi
    push edi
    push ebx

    mov dword esi, src              ; esi <- *src
    cmp dword esi, 0
    je exit

    mov dword edi, dst              ; edi <- *dst
    cmp dword edi, 0
    je exit

    pxor xmm0, xmm0
    push dword dst_row_size
    push dword height
    push dword width
    push dword dst
    call image_init_xmm0
    add esp, 16

    finit
    fild dword axis_y               ; st0 = axis_y
    fabs                            ; st0 = |axis_y| = offs_y
    fld st0                         ; st1 = st0 = offs_y
    fisubr dword height             ; st1 = offs_y, st0 = height - offs_y
    fistp dword rows                ; st0 = offs_y
    fild dword axis_x               ; st1 = offs_y, st0 = axis_x
    fabs                            ; st1 = offs_y, st0 = |axis_x| = offs_x
    fisubr dword width              ; st1 = offs_y, st0 = width - offs_x
    fistp dword cols                ; st1 = offs_y

    cmp dword axis_y, 0
    jl advance_row_src
    jg advance_row_dst
    jmp horizontal_offset

    advance_row_src:
        fild dword src_row_size     ; st0 = offs_y, st1 = src_row_size
        fmulp                       ; st0 = offs_y * src_row_size
        fistp dword fpu_var
        add dword esi, fpu_var
        jmp horizontal_offset

    advance_row_dst:
        fild dword dst_row_size     ; st0 = offs_y, st1 = dst_row_size
        fmulp                       ; st0 = offs_y * dst_row_size
        fistp dword fpu_var
        add dword edi, fpu_var
        jmp horizontal_offset

    horizontal_offset:
        mov dword eax, 0            ; eax = start column for src
        mov dword ebx, 0            ; ebx = start column for dst
        cmp dword axis_x, 0
        jl advance_col_src
        jg advance_col_dst
        jmp translation_cycle

    advance_col_src:
        sub dword eax, axis_x
        jmp translation_cycle

    advance_col_dst:
        add dword ebx, axis_x
        jmp translation_cycle


    translation_cycle:
        cmp dword cols, 0
        jz exit
        cmp dword rows, 0
        jz exit
        cmp dword cols, 16
        jl cycle_rows_1_byte

    cycle_rows:

        cmp dword rows, 0
        je exit

        push dword eax              ; save start column for src
        push dword ebx              ; save start column for dst
        dec dword rows              ; rows = rows left
        mov dword ecx, cols         ; ecx = columns left

        cycle_columns:

            movdqu xmm0, [esi + eax]
            movdqu [edi + ebx], xmm0

            sub dword ecx, 16
            jz next_row

            add dword eax, 16
            add dword ebx, 16
            cmp dword ecx, 17
            jl last_16_pixels
            jmp cycle_columns

            last_16_pixels:
                add dword eax, ecx
                add dword ebx, ecx
                sub dword eax, 16
                sub dword ebx, 16
                mov dword ecx, 16
                jmp cycle_columns

        next_row:

            add dword esi, src_row_size
            add dword edi, dst_row_size
            pop dword ebx           ; restore start column for dst
            pop dword eax           ; restore start column for src
            jmp cycle_rows

    cycle_rows_1_byte:

        cmp dword rows, 0
        je exit

        push dword eax              ; save start column for src
        push dword ebx              ; save start column for dst
        dec dword rows              ; rows = rows left
        mov dword ecx, cols         ; ecx = columns left

        cycle_columns_1_byte:

            mov byte dl, [esi + eax]
            mov byte [edi + ebx], dl
            inc dword eax
            inc dword ebx
            loop cycle_columns_1_byte

        add dword esi, src_row_size
        add dword edi, dst_row_size
        pop dword ebx               ; restore start column for dst
        pop dword eax               ; restore start column for src
        jmp cycle_rows_1_byte

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 12
        pop ebp
        ret

