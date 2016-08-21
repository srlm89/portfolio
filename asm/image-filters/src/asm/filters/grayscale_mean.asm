;-------------------------------------------------------------------------------
; void grayscale_mean(int width,                 | dst_row_size  |    ebp+44
;                     int height,                | src_B_row     |    ebp+40
;                     unsigned char* src_R,      | src_G_row     |    ebp+36
;                     unsigned char* src_G,      | src_R_row     |    ebp+32
;                     unsigned char* src_B,      | dst           |    ebp+28
;                     unsigned char* dst,        | src_B         |    ebp+24
;                     int src_R_row,             | src_G         |    ebp+20
;                     int src_G_row,             | src_R         |    ebp+16
;                     int src_B_row,             | height        |    ebp+12
;                     int dst_row_size);         | width         |    ebp+8
;                                                | dir ret       |    ebp+4
;                                                | EBP           |
;
;   - DST(x,y) = ( SRC_R(x,y) + SRC_G(x,y) + SRC_B(x,y) ) / 3
;-------------------------------------------------------------------------------

extern each_16_bytes_rgb_to_gs, proc_4_dwords_rgb

global grayscale_mean

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

three:                  dd 3.0, 3.0, 3.0, 3.0

grayscale_mean:

    push ebp
    mov ebp, esp

    push dword grayscale_mean_function
    push dword dst_row_size
    push dword src_B_row
    push dword src_G_row
    push dword src_R_row
    push dword dst
    push dword src_B
    push dword src_G
    push dword src_R
    push dword height
    push dword width
    call each_16_bytes_rgb_to_gs
    add esp, 44

    pop ebp
    ret


grayscale_mean_function:

    push dword grayscale_4_dwords
    call proc_4_dwords_rgb
    add esp, 4
    ret


grayscale_4_dwords:

    cvtdq2ps xmm0, xmm0     ; xmm0 <- dwords to float
    cvtdq2ps xmm1, xmm1     ; xmm1 <- dwords to float
    cvtdq2ps xmm2, xmm2     ; xmm2 <- dwords to float
    addps xmm0, xmm1
    addps xmm0, xmm2
    divps xmm0, [three]
    cvtps2dq xmm0, xmm0     ; xmm0 <- floats to dword
    ret

