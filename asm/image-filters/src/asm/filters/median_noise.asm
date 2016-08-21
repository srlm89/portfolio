;-------------------------------------------------------------------------------
; void median_noise(unsigned char* src,      | dst_row_size  |      ebp+28
;                   unsigned char* dst,      | src_row_size  |      ebp+24
;                   int width,               | height        |      ebp+20
;                   int height,              | width         |      ebp+16
;                   int src_row_size,        | dst           |      ebp+12
;                   int dst_row_size);       | src           |      ebp+8
;                                            | dir ret       |      ebp+4
;                                            | EBP           |
;
;-------------------------------------------------------------------------------

extern each_16_bytes_3x3

global median_noise

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]

median_noise:

    push ebp
    mov ebp, esp

    mov dword eax, 0xffffffff       ; eax  = |255,255,255,255|
    movd xmm7, eax                  ; xmm7 = |00 00,00 00,00 00, eax |
    pshufd xmm7, xmm7, 0x00         ; xmm7 = | eax , eax , eax , eax |

    push dword median_noise_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_3x3
    add esp, 28

    pop ebp
    ret


median_noise_function:

    mov dword edx, 5

    median_cycle:

        movdqu xmm0, xmm7                   ; xmm0 = next min
        movdqu xmm5, xmm7                   ; xmm5 = bytes to clear
        mov dword ecx, 9
        push eax

        median_cycle_get_next_min:
            movdqu xmm6, [eax]
            pminub xmm0, xmm6
            add dword eax, 16
        loop median_cycle_get_next_min

        pop eax
        dec dword edx
        jz exit

        mov dword ecx, 9
        push eax

        median_cycle_overwrite_min:
            movdqu xmm6, [eax]              ; xmm6 = read pixel
            movdqu xmm4, xmm0               ; xmm4 = current min
            pcmpeqb xmm4, xmm6              ; xmm4 = mask xmm6 == min
            pand xmm4, xmm5                 ; ignore already cleared
            por xmm6, xmm4                  ; xmm6 = old value or 255 if min
            psubusb xmm5, xmm4              ; update already cleared
            movdqu [eax], xmm6
            add eax, 16
        loop median_cycle_overwrite_min

        pop eax
        jmp median_cycle

    exit:
        ret

