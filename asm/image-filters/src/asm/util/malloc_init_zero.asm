;-------------------------------------------------------------------------------
; void malloc_init_zero(int size);    | size          |      ebp+8
;                                     | dir ret       |      ebp+4
;                                     | EBP           |
;
;   - Gets byte* of specified size
;   - Initializes array in 0
;   - Returns address in EAX
;   - Does not use EBX, ECX, EDX, ESI, EDI
;-------------------------------------------------------------------------------

extern malloc

global malloc_init_zero

%define size [ebp+8]

malloc_init_zero:

    push ebp
    mov ebp, esp
    push ecx

    push dword size
    call malloc
    add esp, 4

    mov dword ecx, size
    initialize_in_zero:
        mov byte [eax + ecx - 1], 0
    loop initialize_in_zero

    pop ecx
    pop ebp
    ret

