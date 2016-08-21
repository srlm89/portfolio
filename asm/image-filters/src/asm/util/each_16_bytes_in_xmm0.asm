;-------------------------------------------------------------------------------
; void each_16_bytes_in_xmm0(                   | function      |    ebp+32
;                    unsigned char* src,        | dst_row_size  |    ebp+28
;                    unsigned char* dst,        | src_row_size  |    ebp+24
;                    int width,                 | height        |    ebp+20
;                    int height,                | width         |    ebp+16
;                    int src_row_size,          | dst           |    ebp+12
;                    int dst_row_size,          | src           |    ebp+8
;                    function* f);              | dir ret       |    ebp+4
;                                               | EBP           |
;
;
;   - Loads 16 bytes from src into XMM0
;   - Calls function (EBX stores column number.)
;   - Writes XMM0 into dst
;   - Does not use EAX, ECX, EDX
;-------------------------------------------------------------------------------

%define src          [ebp+8]
%define dst          [ebp+12]
%define width        [ebp+16]
%define height       [ebp+20]
%define src_row_size [ebp+24]
%define dst_row_size [ebp+28]
%define function     [ebp+32]
%define columns_left [ebp-4]
%define rows_left    [ebp-8]

global each_16_bytes_in_xmm0

each_16_bytes_in_xmm0:

    push ebp
    mov ebp, esp
    sub esp, 8
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

    cycle_rows:

        cmp dword rows_left, 0
        je exit
        dec dword rows_left

        mov dword ebx, width
        mov dword columns_left, ebx
        xor ebx, ebx

        cycle_columns:

            movdqu xmm0, [esi+ebx]  ; xmm0 = |bF,bE,...,b9,b8|b7,b6,...,b1,b0|
            call function           ; xmm0 : |nF,nE,...,n9,n8|n7,n6,...,n1,n0|
            movdqu [edi+ebx], xmm0
            add ebx, 16

            sub dword columns_left, 16
            jz next_row

            cmp dword columns_left, 17
            jl last_16_pixels
            jmp cycle_columns

        last_16_pixels:

            add dword ebx, columns_left
            sub dword ebx, 16
            mov dword columns_left, 16
            jmp cycle_columns

        next_row:

            add dword esi, src_row_size
            add dword edi, dst_row_size
            jmp cycle_rows

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 8
        pop ebp
        ret
