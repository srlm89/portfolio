;-------------------------------------------------------------------------------
; void each_byte_in_eax(unsigned char* src,        | function      |  ebp+32
;                       unsigned char* dst,        | dst_row_size  |  ebp+28
;                       int width,                 | src_row_size  |  ebp+24
;                       int height,                | height        |  ebp+20
;                       int src_row_size,          | width         |  ebp+16
;                       int dst_row_size,          | dst           |  ebp+12
;                       function* f);              | src           |  ebp+8
;                                                  | dir ret       |  ebp+4
;                                                  | EBP           |
;
;   - Loads one byte from src into eax
;   - Calls function
;   - Writes byte from eax into dst
;   - Does not use ECX, EDX
;-------------------------------------------------------------------------------

%define src          [ebp+8]
%define dst          [ebp+12]
%define width        [ebp+16]
%define height       [ebp+20]
%define src_row_size [ebp+24]
%define dst_row_size [ebp+28]
%define function     [ebp+32]
%define rows_left    [ebp-4]

global each_byte_in_eax

each_byte_in_eax:

    push ebp
    mov ebp, esp
    sub esp, 4
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
        xor ebx, ebx

        cycle_columns:

            xor eax, eax            ; eax = |00,00,00,00|
            mov byte al, [esi+ebx]  ; eax = |00,00,00,bb|
            call function           ; eax = |--,--,--,nn|
            mov byte [edi+ebx], al

            inc ebx
            cmp dword ebx, width
            jl cycle_columns

        add dword esi, src_row_size
        add dword edi, dst_row_size
        jmp cycle_rows

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 4
        pop ebp
        ret
