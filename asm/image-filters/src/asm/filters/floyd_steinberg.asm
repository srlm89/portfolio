;-------------------------------------------------------------------------------
; void floyd_steinberg(unsigned char* src,   | dst_row_size |        ebp+28
;                      unsigned char* dst,   | src_row_size |        ebp+24
;                      int width,            | height       |        ebp+20
;                      int height,           | width        |        ebp+16
;                      int src_row_size,     | dst          |        ebp+12
;                      int dst_row_size);    | src          |        ebp+8
;                                            | dir ret      |        ebp+4
;                                            | EBP          |
;
;   - Floyd Steinberg Dithering
;-------------------------------------------------------------------------------

section .bss

    values:             resb 16
    error7:             resd 1
    array:              resd 1
    row_level:          resd 1
    row_above:          resd 1
    deltas:             resd 16


section .text

extern each_16_bytes_in_xmm0, malloc_init_zero, free

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]

dif7:                   dd 0x0007
dif5:                   dd 0x0005
dif3:                   dd 0x0003
dif1:                   dd 0x0001

max_color:              dd 0x00ff
mid_color:              dd 0x007f
min_color:              dd 0x0000

global floyd_steinberg

floyd_steinberg:

    push ebp
    mov ebp, esp

    mov dword ebx, 2
    add dword ebx, width                    ; ebx <- width + 2
    shl dword ebx, 2                        ; ebx <- 4*(width + 2)

    mov dword eax, ebx
    add dword eax, ebx                      ; eax <- 8*(width + 2)
    push eax
    call malloc_init_zero
    mov dword [array], eax
    add esp, 4

    add dword eax, 4
    mov dword [row_level], eax
    add dword eax, ebx
    mov dword [row_above], eax

    push dword floyd_steinberg_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_in_xmm0
    add esp, 28

    push dword [array]
    call free
    add esp, 4

    pop ebp
    ret


floyd_steinberg_function:

    push eax
    push ebx
    push ecx
    push edx
    push esi
    push edi

    shl dword ebx, 2                ; ebx <- offset for dwords instead of bytes
    cmp dword ebx, 0
    jg dithering_ready
        mov eax, [row_level]
        mov ecx, [row_above]
        mov [row_level], ecx
        mov [row_above], eax
    dithering_ready:

    movdqu [values], xmm0
    mov dword edi, [row_level]
    mov dword eax, [edi+ebx-4]
    mov dword [error7], eax
    mov dword edx, 0                ; edx <- word counter
    mov dword edi, [row_above]      ; edi <- pixel deltas
    add dword edi, ebx              ; edi <- columns

    mov dword ecx, 16
    floyd_steinberg_cycle:

        xor eax, eax
        mov byte al, [values+edx]

        mov dword esi, [edi+4*edx-4]
        imul dword esi, [dif1]
        call diffuse_esi_error_to_eax

        mov dword esi, [edi+4*edx]
        imul dword esi, [dif5]
        call diffuse_esi_error_to_eax

        mov dword esi, [edi+4*edx+4]
        imul dword esi, [dif3]
        call diffuse_esi_error_to_eax

        mov dword esi, [error7]
        imul dword esi, [dif7]
        call diffuse_esi_error_to_eax

        call peak_and_saturate_eax
        mov byte [values+edx], al
        mov dword [deltas+4*edx], esi
        mov dword [error7], esi
        add dword edx, 1

    loop floyd_steinberg_cycle

    movdqu xmm0, [values]

    mov dword edi, [row_level]
    add dword edi, ebx
    mov dword esi, 0
    mov dword ecx, 4
    floyd_steinberg_save_deltas:
        movdqu xmm1, [deltas+esi]
        movdqu [edi+esi], xmm1
        add dword esi, 16
    loop floyd_steinberg_save_deltas

    pop edi
    pop esi
    pop edx
    pop ecx
    pop ebx
    pop eax
    ret


diffuse_esi_error_to_eax:

    cmp dword esi, 0
    jl diffuse_error_negative_div
    jmp diffuse_error_positive_div
        diffuse_error_negative_div:
            neg esi
            sar esi, 4
            neg esi
            jmp diffuse_error_after_div
        diffuse_error_positive_div:
            sar esi, 4
    diffuse_error_after_div:

    add dword eax, esi
    cmp dword eax, [max_color]
    cmovg dword eax, [max_color]
    cmp dword eax, [min_color]
    cmovl dword eax, [min_color]
    ret


peak_and_saturate_eax:

    mov dword esi, eax              ; esi <- old_value
    cmp dword eax, [mid_color]
    cmovg dword eax, [max_color]    ; eax  > 127 => eax <- 255
    cmovle dword eax, [min_color]   ; eax <= 127 => eax <- 0
    sub dword esi, eax              ; esi <- old_value - new_value
    ret

