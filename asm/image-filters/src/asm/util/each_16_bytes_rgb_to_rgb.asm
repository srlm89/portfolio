;-------------------------------------------------------------------------------
; void each_16_bytes_rgb_to_rgb(                | function      |    ebp+64
;                    int width,                 | dst_B_row     |    ebp+60
;                    int height,                | dst_G_row     |    ebp+56
;                    unsigned char* src_R,      | dst_R_row     |    ebp+52
;                    unsigned char* src_G,      | dst_B         |    ebp+48
;                    unsigned char* src_B,      | dst_G         |    ebp+44
;                    int src_R_row,             | dst_R         |    ebp+40
;                    int src_G_row,             | src_B_row     |    ebp+36
;                    int src_B_row,             | src_G_row     |    ebp+32
;                    unsigned char* dst_R,      | src_R_row     |    ebp+28
;                    unsigned char* dst_G,      | src_B         |    ebp+24
;                    unsigned char* dst_B,      | src_G         |    ebp+20
;                    int dst_R_row,             | src_R         |    ebp+16
;                    int dst_G_row,             | height        |    ebp+12
;                    int dst_B_row,             | width         |    ebp+8
;                    function* f);              | dir ret       |    ebp+4
;                                               | EBP           |
;
;   - Loads 16 bytes from src_R into XMM0
;   - Loads 16 bytes from src_G into XMM1
;   - Loads 16 bytes from src_B into XMM2
;   - Calls function
;   - Writes XMM0 into dst_R
;   - Writes XMM1 into dst_G
;   - Writes XMM2 into dst_B
;-------------------------------------------------------------------------------

%define width           [ebp+8]
%define height          [ebp+12]
%define src_R           [ebp+16]
%define src_G           [ebp+20]
%define src_B           [ebp+24]
%define src_R_row       [ebp+28]
%define src_G_row       [ebp+32]
%define src_B_row       [ebp+36]
%define dst_R           [ebp+40]
%define dst_G           [ebp+44]
%define dst_B           [ebp+48]
%define dst_R_row       [ebp+52]
%define dst_G_row       [ebp+56]
%define dst_B_row       [ebp+60]
%define function        [ebp+64]

%define columns_left    [ebp-4]
%define rows_left       [ebp-8]

global each_16_bytes_rgb_to_rgb

each_16_bytes_rgb_to_rgb:

    push ebp
    mov ebp, esp
    sub esp, 8
    push esi
    push edi
    push ebx

    mov dword esi, src_R        ; esi <- *src_R
    cmp dword esi, 0
    je exit

    mov dword edi, src_G        ; edi <- *src_G
    cmp dword edi, 0
    je exit

    mov dword ebx, src_B        ; ebx <- *src_B
    cmp dword ebx, 0
    je exit

    cmp dword dst_R, 0
    je exit

    cmp dword dst_G, 0
    je exit

    cmp dword dst_B, 0
    je exit

    mov dword edx, height
    mov dword rows_left, edx

    cycle_rows:

        cmp dword rows_left, 0
        je exit
        dec dword rows_left

        mov dword edx, width
        mov dword columns_left, edx
        xor ecx, ecx

        cycle_columns:

            movdqu xmm0, [esi+ecx]  ; xmm0 = |rF,rE,...,r9,r8|r7,r6,...,r1,r0|
            movdqu xmm1, [edi+ecx]  ; xmm1 = |gF,gE,...,g9,g8|g7,g6,...,g1,g0|
            movdqu xmm2, [ebx+ecx]  ; xmm2 = |bF,bE,...,b9,b8|b7,b6,...,b1,b0|

            push ecx
            call function
            pop ecx

            mov dword edx, dst_R
            movdqu [edx+ecx], xmm0
            mov dword edx, dst_G
            movdqu [edx+ecx], xmm1
            mov dword edx, dst_B
            movdqu [edx+ecx], xmm2
            add ecx, 16

            sub dword columns_left, 16
            jz next_row

            cmp dword columns_left, 17
            jl last_16_pixels
            jmp cycle_columns

        last_16_pixels:

            add dword ecx, columns_left
            sub dword ecx, 16
            mov dword columns_left, 16
            jmp cycle_columns

        next_row:

            mov dword edx, dst_R_row
            add dword dst_R, edx
            mov dword edx, dst_G_row
            add dword dst_G, edx
            mov dword edx, dst_B_row
            add dword dst_B, edx
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

