;-------------------------------------------------------------------------------
; void bluetone(int width,                 | dst_B_row     |    ebp+60
;               int height,                | dst_G_row     |    ebp+56
;               unsigned char* src_R,      | dst_R_row     |    ebp+52
;               unsigned char* src_G,      | dst_B         |    ebp+48
;               unsigned char* src_B,      | dst_G         |    ebp+44
;               int src_R_row,             | dst_R         |    ebp+40
;               int src_G_row,             | src_B_row     |    ebp+36
;               int src_B_row,             | src_G_row     |    ebp+32
;               unsigned char* dst_R,      | src_R_row     |    ebp+28
;               unsigned char* dst_G,      | src_B         |    ebp+24
;               unsigned char* dst_B,      | src_G         |    ebp+20
;               int dst_R_row,             | src_R         |    ebp+16
;               int dst_G_row,             | height        |    ebp+12
;               int dst_B_row);            | width         |    ebp+8
;                                          | dir ret       |    ebp+4
;                                          | EBP           |
;
;   - mean_rgb = (SRC_R(x,y) + SRC_G(x,y) + SRC_B(x,y)) /3
;   - DST_R(x,y) = mean_rgb - 10
;   - DST_G(x,y) = mean_rgb - 10
;   - DST_B(x,y) = mean_rgb + 45
;-------------------------------------------------------------------------------

extern each_16_bytes_rgb_to_rgb, proc_4_dwords_rgb

global bluetone

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

three:                  dd 3.0, 3.0, 3.0, 3.0
minus:                  dd  10,  10,  10,  10
plus:                   dd  55,  55,  55,  55

bluetone:

    push ebp
    mov ebp, esp

    push dword bluetone_function
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


bluetone_function:

    push dword bluetone_4_dwords
    call proc_4_dwords_rgb
    add esp, 4
    ret


bluetone_4_dwords:

    paddd xmm2, xmm1        ; xmm2 <- g + b
    paddd xmm2, xmm0        ; xmm2 <- r + g + b
    cvtdq2ps xmm2, xmm2     ; xmm2 <- dwords to floats
    divps xmm2, [three]     ; xmm2 <- (r + g + b) / 3.0
    cvtps2dq xmm2, xmm2     ; xmm2 <- mean_rgb
    psubd xmm2, [minus]     ; xmm2 <- (mean_rgb - 10)
    movdqu xmm0, xmm2       ; xmm0 <- (mean_rgb - 10)
    movdqu xmm1, xmm2       ; xmm1 <- (mean_rgb - 10)
    paddd xmm2, [plus]      ; xmm2 <- (mean_rgb + 45)
    ret

