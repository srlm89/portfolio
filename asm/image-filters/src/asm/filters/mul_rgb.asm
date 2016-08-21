;-------------------------------------------------------------------------------
; void mul_rgb(int width,                 | blue          |    ebp+72
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
;              float red,                 | src_R         |    ebp+16
;              float green,               | height        |    ebp+12
;              float blue);               | width         |    ebp+8
;                                         | dir ret       |    ebp+4
;                                         | EBP           |
;
;   - DST_R(x,y) = r * SRC_R(x,y)
;   - DST_G(x,y) = g * SRC_G(x,y)
;   - DST_B(x,y) = b * SRC_B(x,y)
;-------------------------------------------------------------------------------

section .bss

    value_r:            resd 4
    value_g:            resd 4
    value_b:            resd 4
    xtmp:               resd 4

section .text

extern each_16_bytes_rgb_to_rgb, proc_4_dwords_rgb

global mul_rgb

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


mul_rgb:

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

    push dword mul_rgb_function
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


mul_rgb_function:

    push dword mul_rgb_4_dwords
    call proc_4_dwords_rgb
    add esp, 4
    ret


mul_rgb_4_dwords:

    cvtdq2ps xmm0, xmm0
    cvtdq2ps xmm1, xmm1
    cvtdq2ps xmm2, xmm2
    movdqu [xtmp], xmm7
    movdqu xmm7, [value_r]
    mulps xmm0, xmm7
    movdqu xmm7, [value_g]
    mulps xmm1, xmm7
    movdqu xmm7, [value_b]
    mulps xmm2, xmm7
    movdqu xmm7, [xtmp]
    cvtps2dq xmm0, xmm0
    cvtps2dq xmm1, xmm1
    cvtps2dq xmm2, xmm2
    ret

