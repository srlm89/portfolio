;-------------------------------------------------------------------------------
; void proc_8_words_xmm0(function* f);    | function      |        ebp+8
;                                         | dir ret       |        ebp+4
;                                         | EBP           |
;
;   - Stores 16 bytes from XMM0 in a buffer
;   - For each group of 8 bytes:
;       - Unpacks 8 bytes into XMM0 as words
;       - Calls function
;   - Packs 16 processed words into XMM0 as bytes
;   - Does not use EAX, EBX, ECX, EDX, ESI, EDI
;-------------------------------------------------------------------------------

section .bss

    copy:           resd 4
    xtmp:           resd 4

section .text

global proc_8_words_xmm0

%define function    [ebp+8]

zero:               dd 0x00000000, 0x00000000, 0x00000000, 0x00000000

proc_8_words_xmm0:

    push ebp
    mov ebp, esp

    movdqu [copy], xmm0
    punpckhbw xmm0, [zero]
    call function
    movdqu [xtmp], xmm0

    movdqu xmm0, [copy]
    punpcklbw xmm0, [zero]
    call function

    movdqu xmm1, [xtmp]
    packuswb xmm0, xmm1

    pop ebp
    ret

