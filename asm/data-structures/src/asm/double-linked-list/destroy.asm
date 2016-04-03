; void destroy(node* list);
;
; |      *list       |    [ebp+8]
; |    ret address   |    [ebp+4]
; |       EBP        |    [ebp]

extern free
global destroy


section .data

%define offs_name  2                        ; name
%define offs_next  14                       ; next node address
%define head [ebp+8]                        ; list head address


section .text

destroy:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx

    mov dword ecx, head
                
    iterative_destroy:
        cmp dword ecx, 0
        je eof
        push dword [ecx + offs_next]
        push dword [ecx + offs_name]
        push dword ecx
        call free                               ; release node's struct
        add esp, 4
        call free                               ; release node's name
        add esp, 4
        pop dword ecx
        jmp iterative_destroy

    eof:
        pop ebx
        pop edi
        pop esi
        pop ebp
        ret

