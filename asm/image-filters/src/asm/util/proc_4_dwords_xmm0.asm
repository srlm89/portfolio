;-------------------------------------------------------------------------------
; void proc_4_dwords_xmm0(function* f);   | function      |        ebp+8
;                                         | dir ret       |        ebp+4
;                                         | EBP           |
;
;   - Stores 16 bytes from XMM0 in a buffer
;   - For each group of 4 bytes:
;       - Unpacks 4 bytes into XMM0 as dwords
;       - Calls function
;   - Packs 16 processed dwords into XMM0 as bytes
;   - Does not use EAX, EBX, ECX, EDX, ESI, EDI
;-------------------------------------------------------------------------------

section .bss

    copy:           resd 4
    tmph:           resd 4
    tmpl:           resd 4

section .text

global proc_4_dwords_xmm0

%define function    [ebp+8]

zero:               dd 0x00000000, 0x00000000, 0x00000000, 0x00000000

proc_4_dwords_xmm0:

    push ebp
    mov ebp, esp

    movdqu [copy], xmm0     ; copy = |bF,bE,...,b1,b0|

    punpckhbw xmm0, [zero]  ; xmm0 = |00 bF,...,00 b8|
    punpckhwd xmm0, [zero]  ; xmm0 = |00 bF,...,00 bC|
    call function
    movdqu [tmph], xmm0     ; tmph = |00 nF,...,00 nC|
    movdqu xmm0, [copy]     ; xmm0 = |bF,bE,...,b1,b0|
    punpckhbw xmm0, [zero]  ; xmm0 = |00 bF,...,00 b8|
    punpcklwd xmm0, [zero]  ; xmm0 = |00 bB,...,00 b8|
    call function
    movdqu xmm1, [tmph]
    packssdw xmm0, xmm1     ; xmm0 = |00 nF,...,00 n8|
    movdqu [tmph], xmm0     ; tmph = |00 nF,...,00 n8|

    movdqu xmm0, [copy]     ; xmm0 = |bF,bE,...,b1,b0|
    punpcklbw xmm0, [zero]  ; xmm0 = |b7,b6,...,b1,b0|
    punpckhwd xmm0, [zero]  ; xmm0 = |00 b7,...,00 b4|
    call function
    movdqu [tmpl], xmm0     ; tmpl = |00 n7,...,00 n4|
    movdqu xmm0, [copy]     ; xmm0 = |bF,bE,...,b1,b0|
    punpcklbw xmm0, [zero]  ; xmm0 = |b7,b6,...,b1,b0|
    punpcklbw xmm0, [zero]  ; xmm0 = |00 b3,...,00 b0|
    call function

    movdqu xmm1, [tmpl]
    packssdw xmm0, xmm1     ; xmm0 = |00 n7,...,00 n0|
    movdqu xmm1, [tmph]
    packuswb xmm0, xmm1     ; xmm0 = |nF,nE,...,n1,n0|

    pop ebp
    ret

