;-------------------------------------------------------------------------------
; void contrast(unsigned char* src,      | contr         |        ebp+32
;               unsigned char* dst,      | dst_row_size  |        ebp+28
;               int width,               | src_row_size  |        ebp+24
;               int height,              | height        |        ebp+20
;               int src_row_size,        | width         |        ebp+16
;               int dst_row_size,        | dst           |        ebp+12
;               int contr);              | src           |        ebp+8
;                                        | dir ret       |        ebp+4
;                                        | EBP           |
;
;   F = (259 * (contr + 255)) / (255 * (259 - contr))
;   DST(x, y) = F * SRC(x, y) + 128 * (1 - F)
;-------------------------------------------------------------------------------

extern each_16_bytes_in_xmm0, proc_4_dwords_xmm0

global contrast

%define src          [ebp+8]
%define dst          [ebp+12]
%define width        [ebp+16]
%define height       [ebp+20]
%define src_row_size [ebp+24]
%define dst_row_size [ebp+28]
%define contr        [ebp+32]

contrast:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx
    push ecx

    mov dword ebx, contr
    mov dword esi, 259

    mov dword edi, 259      ; edi <- 259
    mov dword ecx, 255      ; ecx <- 255
    add dword ebx, 255      ; ebx <- C+255
    sub dword esi, contr    ; esi <- 259-C

    pxor xmm0, xmm0
    pxor xmm1, xmm1
    pxor xmm2, xmm2
    pxor xmm3, xmm3

    cvtsi2ss xmm0, ebx      ; xmm0 = |000 000|000 000|000 000| C+255 |
    cvtsi2ss xmm1, esi      ; xmm1 = |000 000|000 000|000 000| 259-C |
    cvtsi2ss xmm2, edi      ; xmm2 = |000 000|000 000|000 000|  259  |
    cvtsi2ss xmm3, ecx      ; xmm3 = |000 000|000 000|000 000|  255  |

    mulss xmm0, xmm2        ; xmm0 = |000 000|000 000|000 000|(C+255)*259|
    mulss xmm1, xmm3        ; xmm1 = |000 000|000 000|000 000|(259-C)*255|
    divss xmm0, xmm1        ; xmm0 = |000 000|000 000|000 000|   F   |
    pshufd xmm6, xmm0, 0x00 ; xmm6 = |   F   |   F   |   F   |   F   |

    mov dword ecx, 1        ; ecx <- 1
    mov dword ebx, 128      ; ebx = 128

    cvtsi2ss xmm1, ecx      ; xmm1 = |000 000|000 000|000 000|     1     |
    subss xmm1, xmm0        ; xmm1 = |000 000|000 000|000 000|    1-F    |
    cvtsi2ss xmm2, ebx      ; xmm2 = |000 000|000 000|000 000|    128    |
    mulss xmm1, xmm2        ; xmm1 = |000 000|000 000|000 000| 128*(1-F) |
    pshufd xmm7, xmm1, 0x00 ; xmm7 = |128*(1-F)|128*(1-F)|128*(1-F)|128*(1-F)|

    push dword contrast_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_in_xmm0
    add esp, 28

    pop ecx
    pop ebx
    pop edi
    pop esi
    pop ebp
    ret


contrast_function:
    push contrast_4_dwords
    call proc_4_dwords_xmm0
    add esp, 4
    ret


contrast_4_dwords:
    cvtdq2ps xmm0, xmm0     ; xmm0 <- dwords to float
    mulps xmm0, xmm6        ; xmm0 *= F
    addps xmm0, xmm7        ; xmm0 += 128*(1-F)
    cvtps2dq xmm0, xmm0     ; xmm0 <- floats to dword
    ret

