;-------------------------------------------------------------------------------
; void image_init_xmm0(unsigned char* src,  | src_row_size  |       ebp+20
;                      int width,           | height        |       ebp+16
;                      int height,          | width         |       ebp+12
;                      int src_row_size);   | src           |       ebp+8
;                                           | dir ret       |       ebp+4
;                                           | EBP           |
;
;   - Writes all the bytes of image with values from XMM0
;-------------------------------------------------------------------------------

global image_init_xmm0

%define src             [ebp+8]
%define width           [ebp+12]
%define height          [ebp+16]
%define src_row_size    [ebp+20]

image_init_xmm0:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx
    push edx

    mov dword esi, src          ; esi <- *src
    cmp dword esi, 0
    je exit

    mov dword edi, height

    cycle_rows:

        cmp dword edi, 0
        je exit

        dec dword edi           ; edi <- rows left
        mov dword edx, width    ; edx <- columns left
        xor ebx, ebx            ; ebx <- current column

        cycle_columns:

            movdqu [esi + ebx], xmm0
            add dword ebx, 16
            sub dword edx, 16
            jz next_row

            cmp dword edx, 16
            jg cycle_columns

            add dword ebx, edx
            sub dword ebx, 16
            mov dword edx, 16
            jmp cycle_columns

        next_row:

            add dword esi, src_row_size
            jmp cycle_rows

    exit:
        pop edx
        pop ebx
        pop edi
        pop esi
        pop ebp
        ret
