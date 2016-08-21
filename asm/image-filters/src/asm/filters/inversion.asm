;-------------------------------------------------------------------------------
; void inversion(unsigned char* src,     | dst_row_size |        ebp+28
;                unsigned char* dst,     | src_row_size |        ebp+24
;                int width,              | height       |        ebp+20
;                int height,             | width        |        ebp+16
;                int src_row_size,       | dst          |        ebp+12
;                int dst_row_size);      | src          |        ebp+8
;                                        | dir ret      |        ebp+4
;                                        | EBP          |
;   DST(x, y) = 255 - SRC(x, y)
;-------------------------------------------------------------------------------

extern each_16_bytes_in_xmm0

global inversion

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]

ffff:                   dd 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff

inversion:

    push ebp
    mov ebp, esp
    push ebx

    push dword inversion_function
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


inversion_function:

    movdqu xmm1, [ffff]     ; xmm1 = |ff,ff,ff,ff, ... ,ff,ff,ff,ff|
    psubusb xmm1, xmm0      ; xmm1 -= values
    movdqu xmm0, xmm1
    ret

