;-------------------------------------------------------------------------------
; void proc_4_dwords_kernel(qword* k,       | pixels        |     ebp+12
;                           byte* pixels);  | kernel        |     ebp+8
;                                           | dir ret       |     ebp+4
;                                           | EBP           |
;
;   It obtains the product of the pixels by the first 9 numbers of
;   the kernel and then it divides the result. Then, the values are
;   saturated and the 16 processed pixels are copied to XMM0
;
;                         pixels                      kernel[1:9]
;
;   |p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9 p9|   | x1 |
;   |p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8 p8|   | x2 |
;   |p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7 p7|   | x3 |
;   |p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6 p6|   | x4 |
;   |p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5 p5| x | x5 | % kernel[10]
;   |p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4 p4|   | x6 |
;   |p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3 p3|   | x7 |
;   |p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2 p2|   | x8 |
;   |p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1 p1|   | x9 |
;
;   - Does not use EAX, EDX
;   - Uses XMM0, XMM1, XMM2, XMM3
;-------------------------------------------------------------------------------

section .bss

    floats:         resd 16

section .text

global proc_4_dwords_kernel

%define kernel      [ebp+8]
%define pixels      [ebp+12]

zero:                   dd 0x00000000, 0x00000000, 0x00000000, 0x00000000

proc_4_dwords_kernel:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx

    movdqu xmm3, [zero]
    mov dword ebx, floats
    mov dword ecx, 4
    clear_floats:
        movdqu [ebx], xmm3
        add dword ebx, 16
    loop clear_floats

    mov dword esi, pixels               ; esi <- reads from pixels
    mov dword edi, kernel               ; edi <- reads from kernel
    mov dword ecx, 9

    proc_kernel_cycle:

        sub dword ebx, 64               ; ebx <- 16-float buffer
        movdqu xmm1, [esi]              ; xmm1 = 16 bytes from pixels
        movdqu xmm2, [edi]              ; xmm2 = 4 dwords from kernel
        cvtdq2ps xmm2, xmm2             ; xmm2 = 4 floats from kernel

        push dword ecx
        mov dword ecx, 4
        proc_kernel_accumulate_cycle:
            movdqu xmm3, [ebx]          ; xmm3 = accumulated
            movdqu xmm0, xmm1           ; xmm0 = |bF,bE,bD, ... ,b2,b1,b0|
            punpcklbw xmm0, [zero]      ; xmm0 = |00 b7| ... |00 b1|00,b0|
            punpcklwd xmm0, [zero]      ; xmm0 = |00 b3|00 b2|00 b1|00 b0|
            cvtdq2ps xmm0, xmm0         ; xmm0 = | f3  | f2  | f1  | f0  |
            mulps xmm0, xmm2            ; xmm0 *= xmm2
            addps xmm0, xmm3            ; xmm0 += accumulated
            movdqu [ebx], xmm0          ; update accumulated
            psrldq xmm1, 0x4            ; move to next 4 bytes
            add ebx, 16                 ; move to next 4 floats
        loop proc_kernel_accumulate_cycle

        pop dword ecx
        add dword esi, 16               ; move to next 16-pixel pack
        add dword edi, 16               ; move to next  4-dword pack

    loop proc_kernel_cycle

    pxor xmm0, xmm0                     ; xmm0 = |00 00|00 00|00 00|00 00|
    movdqu xmm2, [edi]                  ; xmm2 = last 4 dwords from kernel
    cvtdq2ps xmm2, xmm2                 ; xmm2 = last 4 floats from kernel
    mov dword ecx, 4

    proc_kernel_divide_floats:
        pslldq xmm0, 0x4
        sub ebx, 16                     ; ebx <- back 4 floats
        movdqu xmm1, [ebx]              ; xmm1 = | f3 | f2 | f1 | f0 |
        divps xmm1, xmm2                ; xmm1 /= xmm2
        cvttps2dq xmm1, xmm1            ; xmm1 = |00 b3|00 b2|00 b1|00 b0|
        packssdw xmm1, [zero]           ; xmm1 = |00 00|00 00|b3 b2|b1 b0|
        packuswb xmm1, [zero]           ; xmm1 = |00 00|00 00|00 00|b3,b2,b1,b0|
        por xmm0, xmm1
    loop proc_kernel_divide_floats

    pop ebx
    pop edi
    pop esi
    pop ebp
    ret

