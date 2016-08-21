;-------------------------------------------------------------------------------
; void brightness(unsigned char* src,    | bright        |        ebp+32
;                 unsigned char* dst,    | dst_row_size  |        ebp+28
;                 int width,             | src_row_size  |        ebp+24
;                 int height,            | height        |        ebp+20
;                 int src_row_size,      | width         |        ebp+16
;                 int dst_row_size,      | dst           |        ebp+12
;                 short int bright);     | src           |        ebp+8
;                                        | dir ret       |        ebp+4
;                                        | EBP           |
;
;   DST(x, y) = SRC(x, y) + bright
;-------------------------------------------------------------------------------

extern each_16_bytes_in_xmm0, proc_8_words_xmm0

global brightness

%define src          [ebp+8]
%define dst          [ebp+12]
%define width        [ebp+16]
%define height       [ebp+20]
%define src_row_size [ebp+24]
%define dst_row_size [ebp+28]
%define bright       [ebp+32]

brightness:

    push ebp
    mov ebp, esp
    push ebx

    xor ebx, ebx
    mov word bx, bright         ; ebx  = |00 00,br br|
    shl ebx, 0x10               ; ebx  = |br br,00 00|
    mov word bx, bright         ; ebx  = |br br,br br|
    movd xmm7, ebx              ; xmm7 = |00,00,00,00,00,00,br,br|
    pshufd xmm7, xmm7, 0x00     ; xmm7 = |br,br,br,br,br,br,br,br|

    push dword brightness_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_in_xmm0
    add esp, 28

    pop ebx
    pop ebp
    ret


brightness_function:

    push brightness_8_words
    call proc_8_words_xmm0
    add esp, 4
    ret


brightness_8_words:
    paddsw xmm0, xmm7      ; add bright (signed) to 8 bytes
    ret

