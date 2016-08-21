;-------------------------------------------------------------------------------
; void add_rgb(int width,                 | blue          |    ebp+72
;              int height,                | green         |    ebp+68
;              unsigned char* src_R,      | red           |    ebp+64
;              unsigned char* src_G,      | dst_B_row     |    ebp+60
;              unsigned char* src_B,      | dst_G_row     |    ebp+56
;              int src_R_row,             | dst_R_row     |    ebp+52
;              int src_G_row,             | dst_B         |    ebp+48
;              int src_B_row,             | dst_G         |    ebp+44
;              unsigned char* dst_R,      | dst_R         |    ebp+40
;              unsigned char* dst_G,      | src_B_row     |    ebp+36
;              unsigned char* dst_B,      | src_G_row     |    ebp+32
;              int dst_R_row,             | src_R_row     |    ebp+28
;              int dst_G_row,             | src_B         |    ebp+24
;              int dst_B_row,             | src_G         |    ebp+20
;              int red,                   | src_R         |    ebp+16
;              int green,                 | height        |    ebp+12
;              int blue);                 | width         |    ebp+8
;                                         | dir ret       |    ebp+4
;                                         | EBP           |
;
;   - DST_R(x,y) = SRC_R(x,y) + r
;   - DST_G(x,y) = SRC_G(x,y) + g
;   - DST_B(x,y) = SRC_B(x,y) + b
;-------------------------------------------------------------------------------

section .bss

    value_r:            resd 4
    value_g:            resd 4
    value_b:            resd 4
    xtmp:               resd 4

section .text

extern each_16_bytes_rgb_to_rgb, proc_4_dwords_rgb

global add_rgb

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
%define red             [ebp+64]
%define green           [ebp+68]
%define blue            [ebp+72]


add_rgb:

    push ebp
    mov ebp, esp

    xor eax, eax
    pxor xmm0, xmm0

    mov dword eax, red
    movd xmm0, eax
    pshufd xmm0, xmm0, 0x0
    movdqu [value_r], xmm0

    mov dword eax, green
    movd xmm0, eax
    pshufd xmm0, xmm0, 0x0
    movdqu [value_g], xmm0

    mov dword eax, blue
    movd xmm0, eax
    pshufd xmm0, xmm0, 0x0
    movdqu [value_b], xmm0

    push dword add_rgb_function
    push dword dst_B_row
    push dword dst_G_row
    push dword dst_R_row
    push dword dst_B
    push dword dst_G
    push dword dst_R
    push dword src_B_row
    push dword src_G_row
    push dword src_R_row
    push dword src_B
    push dword src_G
    push dword src_R
    push dword height
    push dword width
    call each_16_bytes_rgb_to_rgb
    add esp, 60

    pop ebp
    ret


add_rgb_function:

    push dword add_rgb_4_dwords
    call proc_4_dwords_rgb
    add esp, 4
    ret


add_rgb_4_dwords:

    movdqu [xtmp], xmm7
    movdqu xmm7, [value_r]
    paddd xmm0, xmm7
    movdqu xmm7, [value_g]
    paddd xmm1, xmm7
    movdqu xmm7, [value_b]
    paddd xmm2, xmm7
    movdqu xmm7, [xtmp]
    ret

