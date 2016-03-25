; void insert(node** list, char* name, short id, float score);
;
; |       score      |    [ebp+20]
; |        id        |    [ebp+16]
; |       name       |    [ebp+12]
; |      **list      |    [ebp+8]
; |    ret address   |    [ebp+4]
; |       EBP        |    [ebp]

extern malloc, copy_string
global insert


section .data

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_prev  10                       ; previous node address
%define offs_next  14                       ; next node address
%define head [ebp+8]                        ; list head address

%define node_size  18                       ; size of node struct in bytes
%define name [ebp+12]                       ; new node name
%define id [ebp+16]                         ; new node id
%define score [ebp+20]                      ; new node score


section .text

insert:
        
    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx

    mov word bx, id                         ; bx <- node id
    mov dword edx, score                    ; edx <- node score
    mov dword edi, head                     ; edi <- previous node address
    mov dword esi, [edi]                    ; esi <- next node address
    cmp dword esi, 0
    je create_list_head

    cmp word bx, [esi + offs_id]
    jl insert_as_head

    search_insertion_place:
        cmp dword esi, 0
        je insert_in_the_end
        cmp word bx, [esi + offs_id]
        jl insert_in_the_middle
        je eof
        mov dword edi, esi
        mov dword esi, [esi + offs_next]
        jmp search_insertion_place

    create_list_head:
        push dword name
        call create_node_with_name
        add esp, 4
        mov word [eax + offs_id], bx
        mov dword [eax + offs_score], edx
        mov dword [eax + offs_next], 0
        mov dword [eax + offs_prev], 0
        mov dword [edi], eax
        jmp eof

    insert_as_head:
        push dword name
        call create_node_with_name
        add esp, 4
        mov word [eax + offs_id], bx
        mov dword [eax + offs_score], edx
        mov dword [eax + offs_next], esi
        mov dword [eax + offs_prev], 0
        mov dword [esi + offs_prev], eax
        mov dword [edi], eax
        jmp eof

    insert_in_the_end:
        push dword name
        call create_node_with_name
        add esp, 4
        mov word [eax + offs_id], bx
        mov dword [eax + offs_score], edx
        mov dword [eax + offs_next], 0
        mov dword [eax + offs_prev], edi
        mov dword [edi + offs_next], eax
        jmp eof

    insert_in_the_middle:
        push dword name
        call create_node_with_name
        add esp, 4
        mov word [eax + offs_id], bx
        mov dword [eax + offs_score], edx
        mov dword [eax + offs_next], esi
        mov dword [eax + offs_prev], edi
        mov dword [edi + offs_next], eax
        mov dword [esi + offs_prev], eax
        jmp eof

    eof:
        pop ebx
        pop edi
        pop esi
        pop ebp
        ret


create_node_with_name:

    push ebp
    mov ebp, esp
    push edx

    push dword [ebp + 8]
    call copy_string
    add esp, 4

    push eax
    push dword node_size
    call malloc
    add esp, 4
    pop edx

    mov dword [eax + offs_name], edx
    pop edx
    pop ebp
    ret

