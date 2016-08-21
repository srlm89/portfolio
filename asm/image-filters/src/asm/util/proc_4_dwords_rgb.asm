;-------------------------------------------------------------------------------
; void proc_4_dwords_rgb(function* f);    | function      |        ebp+8
;                                         | dir ret       |        ebp+4
;                                         | EBP           |
;
;   - Stores XMM0, XMM1 and XMM3 in a buffer
;   - For each group of 4 bytes:
;       - Unpacks 4 bytes into XMM0, XMM1 and XMM3 as dwords
;       - Calls function
;   - Packs 16 processed dwords into XMM0, XMM1 and XMM3 as bytes
;   - Uses XMM5, XMM6 and XMM7
;-------------------------------------------------------------------------------

section .bss

    array:              resd 48

section .text

global proc_4_dwords_rgb

%define function        [ebp+8]

zero:                   dd 0x00000000, 0x00000000, 0x00000000, 0x00000000

proc_4_dwords_rgb:

    push ebp
    mov ebp, esp
    push eax
    push ecx

    movdqu xmm5, xmm0
    movdqu xmm6, xmm1
    movdqu xmm7, xmm2
    mov dword eax, array
    mov dword ecx, 4

    proc_4_dwords_cycle:

        movdqu xmm0, xmm5       ; xmm0 <- |00,00,..,00|r3,r2,r1,r0|
        movdqu xmm1, xmm6       ; xmm1 <- |00,00,..,00|g3,g2,g1,g0|
        movdqu xmm2, xmm7       ; xmm2 <- |00,00,..,00|b3,b2,b1,b0|
        punpcklbw xmm0, [zero]  ; xmm0 <- |00 r3|00 r2|00 r1|00 r0|
        punpcklbw xmm1, [zero]  ; xmm1 <- |00 g3|00 g2|00 g1|00 g0|
        punpcklbw xmm2, [zero]  ; xmm2 <- |00 b3|00 b2|00 b1|00 b0|
        punpcklwd xmm0, [zero]  ; xmm0 <- |  r3 |  r2 |  r1 |  r0 |
        punpcklwd xmm1, [zero]  ; xmm1 <- |  g3 |  g2 |  g1 |  g0 |
        punpcklwd xmm2, [zero]  ; xmm2 <- |  b3 |  b2 |  b1 |  b0 |

        call function

        movdqu [eax], xmm0
        movdqu [eax+64], xmm1
        movdqu [eax+128], xmm2
        add dword eax, 16

        psrldq xmm5, 0x4
        psrldq xmm6, 0x4
        psrldq xmm7, 0x4

    loop proc_4_dwords_cycle

    mov dword eax, array        ; eax <- byte[0:63]
    call pack_4_dwords_to_xmm7
    movdqu xmm0, xmm7

    add eax, 64                 ; eax <- byte[64:127]
    call pack_4_dwords_to_xmm7
    movdqu xmm1, xmm7

    add eax, 64                 ; eax <- byte[128:191]
    call pack_4_dwords_to_xmm7
    movdqu xmm2, xmm7

    pop ecx
    pop eax
    pop ebp
    ret


pack_4_dwords_to_xmm7:

    movdqu xmm5, [eax+48]       ; xmm5 <- | bF | bE | bD | bC |
    movdqu xmm6, [eax+32]       ; xmm6 <- | bB | bA | b9 | b8 |
    packssdw xmm6, xmm5         ; xmm6 <- | bF | .. | b9 | b8 |
    movdqu xmm5, [eax+16]       ; xmm5 <- | b7 | b6 | b5 | b4 |
    movdqu xmm7, [eax]          ; xmm7 <- | b3 | b2 | b1 | b0 |
    packssdw xmm7, xmm5         ; xmm7 <- | b7 | .. | b1 | b0 |
    packuswb xmm7, xmm6         ; xmm7 <- | bF | .. | b1 | b0 |
    ret

