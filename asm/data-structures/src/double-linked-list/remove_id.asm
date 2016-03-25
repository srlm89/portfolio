; void remove_id(node** list, short id);
;
; |       id        |    [ebp+12]
; |     **list      |    [ebp+8]
; |    ret address  |    [ebp+4]
; |       EBP       |    [ebp]

extern free
global remove_id


section .data

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_prev  10                       ; previous node address
%define offs_next  14                       ; next node address
%define head [ebp+8]                        ; list head address
%define id [ebp+12]                         ; id value

section .text

remove_id:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx

    mov dword edx, head                     ; edx <- list head
    mov dword ecx, [edx]                    ; ecx <- current node
    cmp dword ecx, 0
    je eof

    mov word bx, id                         ; bx <- target id
    cmp word [ecx + offs_id], bx
    je remove_list_head

    search_node_to_remove:
        cmp dword ecx, 0
        je eof
        cmp word [ecx + offs_id], bx
        je remove_list_node
        mov dword esi, ecx                  ; esi <- previous node address
        mov dword ecx, [ecx + offs_next]
        jmp search_node_to_remove

    remove_list_head:
        mov dword edi, [ecx + offs_next]
        mov dword [edx], edi
        cmp dword edi, 0
        je free_node_memory
        mov dword [edi + offs_prev], 0
        jmp free_node_memory

    remove_list_node:
        mov dword edi, [ecx + offs_next]
        mov dword [esi + offs_next], edi
        cmp dword edi, 0
        je free_node_memory
        mov dword [edi + offs_prev], esi
        jmp free_node_memory

    free_node_memory:
        push dword [ecx + offs_name]
        push dword ecx
        call free                           ; free node structure
        add esp, 4
        call free                           ; free node name
        add esp, 4

    eof:
        pop ebx
        pop edi
        pop esi
        pop ebp
        ret

