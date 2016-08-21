;-------------------------------------------------------------------------------
; void gamma_correction(unsigned char* src,   | gamma        |      ebp+32
;                       unsigned char* dst,   | dst_row_size |      ebp+28
;                       int width,            | src_row_size |      ebp+24
;                       int height,           | height       |      ebp+20
;                       int src_row_size,     | width        |      ebp+16
;                       int dst_row_size,     | dst          |      ebp+12
;                       float gamma);         | src          |      ebp+8
;                                             | dir ret      |      ebp+4
;                                             | EBP          |
;
;   EQG(q) = 2 ^ (log_2(q) / gamma)
;   DST(x, y) = EQG(SRC(x,y)) * 255 / EQG(255) = EQG(SRC(x,y)) * F
;-------------------------------------------------------------------------------

section .bss

    fpu_gamma:          resd 1
    fpu_var_1:          resd 1


section .text

extern each_16_bytes_in_xmm0, proc_4_dwords_xmm0

global gamma_correction

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]
%define gamma           [ebp+32]
%define pow_of_two      [ebp+36]

max_color:              dd 255.0

gamma_correction:

    push ebp
    mov ebp, esp

    mov eax, gamma
    mov dword [fpu_gamma], eax

    finit

    push dword [max_color]
    call calculate_eqg_in_var_1
    add esp, 4

    fld dword [fpu_var_1]           ; st0 = EQG(255)
    fld dword [max_color]           ; st1 = EQG(255), st0 = 255
    fdivrp                          ; st0 = 255/EQG(255) = F
    fstp dword [fpu_var_1]          ; fpu_var_1 = F

    movss xmm7, [fpu_var_1]         ; xmm7 = |00 00,00 00,00 00,  F  |
    pshufd xmm7, xmm7, 0x00         ; xmm7 = |  F  ,  F  ,  F  ,  F  |

    push dword gamma_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_in_xmm0
    add esp, 28

    pop ebp
    ret


gamma_function:
    push gamma_4_dwords
    call proc_4_dwords_xmm0
    add esp, 4
    ret


gamma_4_dwords:
    push ebp
    mov ebp, esp
    sub esp, 16

    cvtdq2ps xmm0, xmm0             ; xmm0 <- dwords to float

    movdqu [ebp-16], xmm0
    mov dword ecx, -16

    dword_cycle:
        push ecx
        push dword [ebp + ecx]
        call calculate_eqg_in_var_1
        add esp, 4
        pop ecx
        mov dword eax, [fpu_var_1]
        mov dword [ebp + ecx], eax
        add ecx, 4
        cmp dword ecx, 0
        jl dword_cycle

    movdqu xmm0, [ebp-16]
    mulps xmm0, xmm7
    cvtps2dq xmm0, xmm0             ; xmm0 <- floats to dword

    add esp, 16
    pop ebp
    ret


calculate_eqg_in_var_1:

    push ebp
    mov ebp, esp

    fld dword [fpu_gamma]           ; st0 = gamma
    fld1                            ; st1 = gamma , st0 = 1
    fdivrp                          ; st0 = 1/gamma
    fld dword [ebp+8]               ; st1 = 1/gamma, st0 = q
    fyl2x                           ; st0 = 1/gamma * log_2(q) = qlog
    fld st0                         ; st1 = st0 = qlog = int_qlog + dec_qlog
    frndint                         ; st1 = qlog, st0 = (int) qlog = int_qlog
    fcomi st0, st1
    jbe get_single_precision_pow
    fld1                            ; st2 = qlog, st1 = int_qlog + 1, st0 = 1
    fsubp                           ; st1 = qlog, st0 = int_qlog

    get_single_precision_pow:
        fist dword [fpu_var_1]      ; fpu_var_1 = int_qlog
        mov dword eax, [fpu_var_1]  ; eax = int_qlog
        add dword eax, 0x7F         ; eax = int_qlog + 127
        shl dword eax, 0x17         ; eax = 2^(int_qlog) (23-bit mantissa)
        mov dword [fpu_var_1], eax

    fsubp                           ; st0 = qlog - int_qlog = dec_qlog
    f2xm1                           ; st0 = 2^(dec_qlog) - 1
    fld1
    faddp                           ; st0 = 2^(dec_qlog)
    fld dword [fpu_var_1]           ; st1 = 2^(dec_qlog), st0 = 2^(int_qlog)
    fmulp                           ; st0 = 2^(qlog)
    fstp dword [fpu_var_1]

    pop ebp
    ret

