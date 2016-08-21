;-------------------------------------------------------------------------------
; void scale(unsigned char* src,          | method       |        ebp+48
;            int srcw,                    | scale_ver    |        ebp+44
;            int srch,                    | scale_hor    |        ebp+40
;            int src_row_size,            | dst_row_size |        ebp+36
;            unsigned char* dst,          | dsth         |        ebp+32
;            int dstw,                    | dstw         |        ebp+28
;            int dsth,                    | dst          |        ebp+24
;            int dst_row_size,            | src_row_size |        ebp+20
;            float scale_hor,             | srch         |        ebp+16
;            float scale_ver,             | srcw         |        ebp+12
;            int method);                 | src          |        ebp+8
;                                         | dir ret      |        ebp+4
;                                         | EBP          |
;
;   - DST(x,y) = S(x / scale_vertical, y / scale_horizontal)
;-------------------------------------------------------------------------------
section .bss

    fpu_scale_horizontal:     resd 1
    fpu_scale_vertical:       resd 1

section .text

extern geometric_filter_each

global scale

%define src             [ebp+8]
%define src_width       [ebp+12]
%define src_height      [ebp+16]
%define src_row_size    [ebp+20]
%define dst             [ebp+24]
%define dst_width       [ebp+28]
%define dst_height      [ebp+32]
%define dst_row_size    [ebp+36]
%define scale_hor       [ebp+40]
%define scale_ver       [ebp+44]
%define interpolation   [ebp+48]

scale:

    push ebp
    mov ebp, esp

    finit
    mov dword eax, scale_hor
    mov dword [fpu_scale_horizontal], eax
    mov dword eax, scale_ver
    mov dword [fpu_scale_vertical], eax

    push dword interpolation
    push dword calculate_coordinate_y
    push dword calculate_coordinate_x
    push dword dst_row_size
    push dword dst_height
    push dword dst_width
    push dword dst
    push dword src_row_size
    push dword src_height
    push dword src_width
    push dword src
    call geometric_filter_each
    add esp, 44

    pop ebp
    ret


calculate_coordinate_y:

    push ebp
    mov ebp, esp
    sub esp, 4

    fild dword [ebp+12]                 ; st0 = y
    fld dword [fpu_scale_horizontal]    ; st1 = y, st0 = scale_h
    fdivp                               ; st0 = y / scale_h
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret


calculate_coordinate_x:

    push ebp
    mov ebp, esp
    sub esp, 4

    fild dword [ebp+8]                  ; st0 = x
    fld dword [fpu_scale_vertical]      ; st1 = x, st0 = scale_v
    fdivp                               ; st0 = x / scale_v
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret

