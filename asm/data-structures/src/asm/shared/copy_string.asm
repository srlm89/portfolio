; void copy_string(char* string);
;
; |     *string      |    [ebp+8]
; |   ret address    |    [ebp+4]
; |       EBP        |    [ebp]

extern malloc
global copy_string


copy_string:

    push ebp
    mov ebp, esp
    push ebx
    push ecx
    push edx
    push esi

    mov dword ebx, [ebp + 8]                ; ebx <- string address
    mov ecx, ebx
    xor edx, edx

    name_length:
        inc edx                             ; edx <- string length
        cmp byte [ecx], 0
        je alloc_memory
        inc ecx
        jmp name_length

    alloc_memory:
        push dword edx
        push dword edx
        call malloc                         ; eax <- allocated memory address
        add esp, 4
        pop dword edx

    mov dword ecx, ebx
    xor esi, esi

    copy_characters:
        cmp dword edx, 0
        je finished_copying
        mov byte bl, [ecx]
        mov byte [eax + esi], bl
        inc esi
        inc ecx
        dec edx
        jmp copy_characters

    finished_copying:
        pop esi
        pop edx
        pop ecx
        pop ebx
        pop ebp
        ret

