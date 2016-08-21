;-------------------------------------------------------------------------------
; void equalization(unsigned char* src,    | dst_row_size  |      ebp+28
;                   unsigned char* dst,    | src_row_size  |      ebp+24
;                   int width,             | height        |      ebp+20
;                   int height,            | width         |      ebp+16
;                   int src_row_size,      | dst           |      ebp+12
;                   int dst_row_size);     | src           |      ebp+8
;                                          | dir ret       |      ebp+4
;                                          | EBP           |
;
;   - Get histogram of picture:
;       H(c) = # of pixels with color c (0 <= c <= 255)
;   - Stretch histogram:
;       E[n] = 0
;       acc = H[0]
;       for c in [1,254]:
;           E[pixel] = (acc * 255) / (height * width)
;           acc += H[c]
;       E[255] = 255
;   - DST(x,y) = E(SRC(x,y))
;-------------------------------------------------------------------------------
section .bss

    array_pointer:     resd 1

section .text

extern malloc_init_zero, free, each_byte_in_eax

global equalization

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]
%define fpu_var_1       [ebp-4]
%define fpu_var_2       [ebp-8]

max_color:              dq 255.0


equalization:

    push ebp
    mov ebp, esp
    sub esp, 8
    push ebx

    mov dword eax, 2048
    push dword 2048
    call malloc_init_zero           ; eax <- array of 512 integers
    add esp, 4
    mov dword [array_pointer], eax

    push dword eax
    push dword histogram_build_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_byte_in_eax
    add esp, 28

    pop dword eax                   ; eax <- array H[c]
    mov dword edx, eax
    add dword edx, 1024             ; edx <- array E[n]
    mov dword [array_pointer], edx

    finit
    mov dword ebx, [eax]
    mov dword fpu_var_2, ebx

    mov dword ecx, 1
    histogram_stretching_cycle:
        fild dword fpu_var_2        ; st0 = acc
        fld qword [max_color]       ; st1 = acc, st0 = 255
        fmulp                       ; st0 = 255*acc
        fild dword height           ; st1 = 255*acc, st0 = h
        fild dword width            ; st2 = 255*acc, st1 = h, st0 = w
        fmulp                       ; st1 = 255*acc, st0 = h*w
        fdivp                       ; st0 = (255*acc)/(h*w)
        fistp dword fpu_var_1       ; fpu_var_1 <- (255*acc)/(h*w)
        mov dword ebx, fpu_var_1
        mov dword [edx + 4*ecx], ebx
        mov dword ebx, [eax + 4*ecx]
        add dword fpu_var_2, ebx
        inc dword ecx
        cmp dword ecx, 255
        jl histogram_stretching_cycle
    mov dword [edx + 4*ecx], 255

    push dword eax
    push dword update_values_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_byte_in_eax
    add esp, 28

    call free
    add esp, 4

    pop ebx
    add esp, 8
    pop ebp
    ret


histogram_build_function:
    mov dword ecx, [array_pointer]  ; ecx <- array H[c]
    inc dword [ecx + 4*eax]         ; count single byte
    ret


update_values_function:
    mov dword ecx, [array_pointer]  ; ecx <- array E[n]
    mov byte al, [ecx + 4*eax]      ; eax <- E[al]
    ret

