;-------------------------------------------------------------------------------
; void shear(unsigned char* src,      | method       |        ebp+48
;            int srcw,                | shear_ver    |        ebp+44
;            int srch,                | shear_hor    |        ebp+40
;            int src_row_size,        | dst_row_size |        ebp+36
;            unsigned char* dst,      | dsth         |        ebp+32
;            int dstw,                | dstw         |        ebp+28
;            int dsth,                | dst          |        ebp+24
;            int dst_row_size,        | src_row_size |        ebp+20
;            float shear_horizontal,  | srch         |        ebp+16
;            float shear_vertical,    | srcw         |        ebp+12
;            int method);             | src          |        ebp+8
;                                     | dir ret      |        ebp+4
;                                     | EBP          |
;
;   - Horizontal shear:
;                 / SRC(x, y - i_y*x)           if i_y >= 0
;     DST(x,y) = <
;                 \ SRC(x, h*i_y + y - i_y*x)   if i_y  < 0
;
;   - Vertical shear:
;                 / SRC(x - i_x*y, y)           if i_x >= 0
;     DST(x,y) = <
;                 \ SRC(w*i_x + x - i_x*y, y)   if i_x  < 0
;-------------------------------------------------------------------------------

section .bss

    fpu_i_x:        resd 1
    fpu_i_y:        resd 1
    fpu_offset_x:   resd 1
    fpu_offset_y:   resd 1

section .text

extern geometric_filter_each

global shear

%define src             [ebp+8]
%define src_width       [ebp+12]
%define src_height      [ebp+16]
%define src_row_size    [ebp+20]
%define dst             [ebp+24]
%define dst_width       [ebp+28]
%define dst_height      [ebp+32]
%define dst_row_size    [ebp+36]
%define i_y             [ebp+40]
%define i_x             [ebp+44]
%define interpolation   [ebp+48]

shear:

    push ebp
    mov ebp, esp

    mov dword eax, i_x
    mov dword [fpu_i_x], eax

    mov dword eax, i_y
    mov dword [fpu_i_y], eax

    push dword src_width
    push dword i_x
    call multiply_and_set_if_negative
    mov dword [fpu_offset_x], eax
    add esp, 8

    push dword src_height
    push dword i_y
    call multiply_and_set_if_negative
    mov dword [fpu_offset_y], eax
    add esp, 8

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


multiply_and_set_if_negative:

    push ebp
    mov ebp, esp
    sub esp, 4

    fld dword [ebp+8]           ; st0 = i_x
    fldz                        ; st1 = i_x, st0 = 0
    fcomi st0, st1
    jb skip_multiplier
    fiadd dword [ebp+12]        ; st1 = i_x, st0 = width

    skip_multiplier:
    fmulp                       ; st0 = i_x*0 o i_x*srcalto
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret


calculate_coordinate_y:

    push ebp
    mov ebp, esp
    sub esp, 4

    fild dword [ebp+12]         ; st0 = y
    fild dword [ebp+8]          ; st1 = y, st0 = x
    fld dword [fpu_i_y]         ; st2 = y, st1 = x, st0 = i_y
    fmulp                       ; st1 = y, st0 = i_y*x
    fsubp                       ; st0 = y - i_y*x
    fadd dword [fpu_offset_y]   ; st0 = (h*i_y or 0) + y - i_y*x
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret


calculate_coordinate_x:

    push ebp
    mov ebp, esp
    sub esp, 4

    fild dword [ebp+8]          ; st0 = x
    fild dword [ebp+12]         ; st1 = x, st0 = y
    fld dword [fpu_i_x]         ; st2 = x, st1 = y, st0 = i_x
    fmulp                       ; st1 = x, st0 = i_x*y
    fsubp                       ; st0 = x - i_x*y
    fadd dword [fpu_offset_x]   ; st0 = (w*i_x or 0) + x - i_x*y
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret

