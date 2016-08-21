;-------------------------------------------------------------------------------
; void rotation(unsigned char* src,   | method       |        ebp+44
;               int srcw,             | alpha        |        ebp+40
;               int srch,             | dst_row_size |        ebp+36
;               int src_row_size,     | dsth         |        ebp+32
;               unsigned char* dst,   | dstw         |        ebp+28
;               int dstw,             | dst          |        ebp+24
;               int dsth,             | src_row_size |        ebp+20
;               int dst_row_size,     | srch         |        ebp+16
;               float alpha,          | srcw         |        ebp+12
;               int method);          | src          |        ebp+8
;                                     | dir ret      |        ebp+4
;                                     | EBP          |
;
;   - c_x = (height(SRC) - 1) / 2
;   - c_y = (width(SRC)  - 1) / 2
;   - d_x = (height(SRC) - height(DST)) / 2
;   - d_y = (width(SRC)  -  width(DST)) / 2
;   - v(x,y) = x*cos(a) + y*sin(a) + sin(a)*(d_y - c_y) + cos(a)*(d_x - c_x)
;   - h(x,y) = y*cos(a) - x*sin(a) + sin(a)*(c_x - d_x) + cos(a)*(d_y - c_y)
;   - DST(x,y) = SRC(c_x + v(x, y), c_y + h(x, y))
;-------------------------------------------------------------------------------

section .bss

    fpu_sin:        resq 1
    fpu_cos:        resq 1
    fpu_off_x:      resq 1
    fpu_off_y:      resq 1

section .text

extern geometric_filter_each

global rotation

%define src             [ebp+8]
%define src_width       [ebp+12]
%define src_height      [ebp+16]
%define src_row_size    [ebp+20]
%define dst             [ebp+24]
%define dst_width       [ebp+28]
%define dst_height      [ebp+32]
%define dst_row_size    [ebp+36]
%define alpha           [ebp+40]
%define interpolation   [ebp+44]

two:                    dd 2

rotation:

    push ebp
    mov ebp, esp

    finit
    fld1                    ; st0 = 1
    fisubr dword src_width  ; st0 = (srcw-1)
    fidiv dword [two]       ; st0 = c_y

    fld1                    ; st1 = c_y, st0 = 1
    fisubr dword src_height ; st1 = c_y, st0 = (srch-1)
    fidiv dword [two]       ; st1 = c_y, st0 = c_x

    fild dword src_width    ; st2 = c_y, st1 = c_x, st0 = srcw
    fisub dword dst_width   ; st2 = c_y, st1 = c_x, st0 = (srcw-dstw)
    fidiv dword [two]       ; st2 = c_y, st1 = c_x, st0 = d_y

    fild dword src_height   ; st3 = c_y, st2 = c_x, st1 = d_y, st0 = srch
    fisub dword dst_height  ; st3 = c_y, st2 = c_x, st1 = d_y, st0 = (srch-dstw)
    fidiv dword [two]       ; st3 = c_y, st2 = c_x, st1 = d_y, st0 = d_x

    fld dword alpha         ; ... st0 = alpha
    fcos                    ; ... st0 = cos(alpha) = cos
    fst qword [fpu_cos]

    fld dword alpha         ; ... st1 = cos, st0 = alpha
    fsin                    ; ... st1 = cos, st0 = sin(alpha) = sin
    fst qword [fpu_sin]

    ; st6 = c_y, st5 = c_x, st4 = d_y, st3 = d_x, st2 = cos, st1 = sin
    fldz                    ; st0 = 0
    fadd st5                ; st0 = c_x
    fsub st3                ; st0 = (c_x - d_x)
    fmul st1                ; st0 = sin(a)*(c_x - d_x)
    fstp qword [fpu_off_y]
    fldz                    ; st0 = 0
    fadd st4                ; st0 = d_y
    fsub st6                ; st0 = (d_y-c_y)
    fmul st2                ; st0 = cos(a)*(d_y-c_y)
    fadd qword [fpu_off_y]  ; st0 = sin(a)*(c_x-d_x) + cos(a)*(d_y-c_y)
    fadd st6                ; st0 = c_y + sin(a)*(c_x-d_x) + cos(a)*(d_y-c_y)
    fstp qword [fpu_off_y]

    ; st6 = c_y, st5 = c_x, st4 = d_y, st3 = d_x, st2 = cos, st1 = sin
    fldz                    ; st0 = 0
    fadd st4                ; st0 = d_y
    fsub st6                ; st0 = (d_y-c_y)
    fmul st1                ; st0 = sin(a)*(d_y-c_y)
    fstp qword [fpu_off_x]
    fldz                    ; st0 = 0
    fadd st3                ; st0 = d_x
    fsub st5                ; st0 = (d_x-c_x)
    fmul st2                ; st0 = cos(a)*(d_x-c_x)
    fadd qword [fpu_off_x]  ; st0 = sin(a)*(d_y-c_y) + cos(a)*(d_x-c_x)
    fadd st5                ; st0 = c_x + sin(a)*(d_y-c_y) + cos(a)*(d_x-c_x)
    fstp qword [fpu_off_x]

    finit
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

    fild dword [ebp+12]     ; st0 = y
    fmul qword [fpu_cos]    ; st0 = y*cos(a)
    fild dword [ebp+8]      ; st1 = y*cos(a), st0 = x
    fmul qword [fpu_sin]    ; st1 = y*cos(a), st0 = x*sin(a)
    fsubp st1, st0          ; st0 = y*cos(a) - x*sin(a)
    fadd qword [fpu_off_y]
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret


calculate_coordinate_x:

    push ebp
    mov ebp, esp
    sub esp, 4

    fild dword [ebp+8]      ; st0 = x
    fmul qword [fpu_cos]    ; st0 = x*cos(a)
    fild dword [ebp+12]     ; st0 = x*cos(a), st1 = y
    fmul qword [fpu_sin]    ; st0 = x*cos(a), st1 = y*sin(a)
    faddp                   ; st0 = x*cos(a) + y*sin(a)
    fadd qword [fpu_off_x]
    fstp dword [ebp-4]

    mov dword eax, [ebp-4]
    add esp, 4
    pop ebp
    ret

