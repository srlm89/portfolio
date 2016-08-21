;-------------------------------------------------------------------------------
; void each_16_bytes_rgb_to_gs(                 | function      |    ebp+48
;                    int width,                 | dst_row_size  |    ebp+44
;                    int height,                | src_B_row     |    ebp+40
;                    unsigned char* src_R,      | src_G_row     |    ebp+36
;                    unsigned char* src_G,      | src_R_row     |    ebp+32
;                    unsigned char* src_B,      | dst           |    ebp+28
;                    unsigned char* dst,        | src_B         |    ebp+24
;                    int src_R_row,             | src_G         |    ebp+20
;                    int src_G_row,             | src_R         |    ebp+16
;                    int src_B_row,             | height        |    ebp+12
;                    int dst_row_size,          | width         |    ebp+8
;                    function* f);              | dir ret       |    ebp+4
;                                               | EBP           |
;
;   - Loads 16 bytes from src_R into XMM0
;   - Loads 16 bytes from src_G into XMM1
;   - Loads 16 bytes from src_B into XMM2
;   - Calls function
;   - Writes XMM0 into dst
;-------------------------------------------------------------------------------

%define width           [ebp+8]
%define height          [ebp+12]
%define src_R           [ebp+16]
%define src_G           [ebp+20]
%define src_B           [ebp+24]
%define dst             [ebp+28]
%define src_R_row       [ebp+32]
%define src_G_row       [ebp+36]
%define src_B_row       [ebp+40]
%define dst_row_size    [ebp+44]
%define function        [ebp+48]

%define columns_left    [ebp-4]
%define rows_left       [ebp-8]

global each_16_bytes_rgb_to_gs

each_16_bytes_rgb_to_gs:

    push ebp
    mov ebp, esp
    sub esp, 8
    push esi
    push edi
    push ebx

    mov dword edx, dst          ; edx <- *dst
    cmp dword edx, 0
    je exit

    mov dword esi, src_R        ; esi <- *src_R
    cmp dword esi, 0
    je exit

    mov dword edi, src_G        ; edi <- *src_G
    cmp dword edi, 0
    je exit

    mov dword ebx, src_B        ; ebx <- *src_B
    cmp dword ebx, 0
    je exit

    mov dword eax, height
    mov dword rows_left, eax

    cycle_rows:

        cmp dword rows_left, 0
        je exit
        dec dword rows_left

        mov dword eax, width
        mov dword columns_left, eax
        xor eax, eax

        cycle_columns:

            movdqu xmm0, [esi+eax]  ; xmm0 = |rF,rE,...,r9,r8|r7,r6,...,r1,r0|
            movdqu xmm1, [edi+eax]  ; xmm1 = |gF,gE,...,g9,g8|g7,g6,...,g1,g0|
            movdqu xmm2, [ebx+eax]  ; xmm2 = |bF,bE,...,b9,b8|b7,b6,...,b1,b0|

            push edx
            push eax
            call function           ; xmm0 : |nF,nE,...,n9,n8|n7,n6,...,n1,n0|
            pop eax
            pop edx

            movdqu [edx+eax], xmm0
            add eax, 16

            sub dword columns_left, 16
            jz next_row

            cmp dword columns_left, 17
            jl last_16_pixels
            jmp cycle_columns

        last_16_pixels:

            add dword eax, columns_left
            sub dword eax, 16
            mov dword columns_left, 16
            jmp cycle_columns

        next_row:

            add dword edx, dst_row_size
            add dword esi, src_R_row
            add dword edi, src_G_row
            add dword ebx, src_B_row
            jmp cycle_rows

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 8
        pop ebp
        ret

