; void remove_id(node **tree, short id);
;
; |        id        |    [ebp+12]
; |      **tree      |    [ebp+8]
; |   ret address    |    [ebp+4]
; |       EBP        |    [ebp]

extern malloc, free
global remove_id


section .data

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_left  10                       ; node* left
%define offs_right 14                       ; node* right

%define root [ebp+8]                        ; root address
%define id [ebp+12]                         ; id value
%define origin [ebp-16]                     ; parent link offset
%define substitute [ebp-20]                 ; substitute for parent

section .text

remove_id:

    push ebp
    mov ebp, esp
    push edi
    push esi
    push ebx
    sub esp, 8

    mov edi, root
    mov edi, [edi]                          ; edi <- node address
    xor ebx, ebx                            ; ebx <- node parent address
    xor ecx, ecx
    mov dword origin, 0

    binary_search:
        cmp dword edi, 0
        je eof
        mov word cx, [edi + offs_id]
        cmp word cx, id
        jg move_left
        jl move_right
        je remove

    move_left:
        mov ebx, edi
        mov dword origin, offs_left
        mov dword edi, [edi + offs_left]
        jmp binary_search

    move_right:
        mov ebx, edi
        mov dword origin, offs_right
        mov dword edi, [edi+offs_right]
        jmp binary_search

    remove:
        mov eax, [edi + offs_left]          ; eax <- left subtree address
        mov ecx, [edi + offs_right]         ; ecx <- right subtree address
        cmp dword ecx, 0
        je no_right_subtree
        cmp dword eax, 0
        je only_right_subtree
        jmp both_subtrees

    no_right_subtree:
        cmp dword eax, 0
        mov dword substitute, 0
        je unlink_and_remove
        jmp only_left_subtree

    unlink_and_remove:
        cmp dword ebx, 0
        je unlink_and_remove_root
        mov dword eax, origin
        mov dword ecx, substitute
        mov dword [ebx + eax], ecx          ; set substitute for parent
        jmp free_node_memory

    unlink_and_remove_root:
        mov dword eax, root
        mov dword ecx, substitute
        mov dword [eax], ecx
        jmp free_node_memory

    free_node_memory:
        push dword [edi + offs_name]
        call free                           ; free memory from name
        add esp, 4
        push dword edi
        call free                           ; free memory from struct
        add esp, 4
        jmp eof

    only_right_subtree:
        mov dword substitute, ecx
        jmp unlink_and_remove

    only_left_subtree:
        mov dword substitute, eax
        jmp unlink_and_remove

    both_subtrees:
        mov edx, [edi + offs_right]         ; edx <- successor node address

        find_successor:
            cmp dword [edx + offs_left],0
            je replace_with_successor
            mov edx, [edx+offs_left]
            jmp find_successor

    replace_with_successor:

        push word [edx + offs_id]           ; save successor id
        push dword [edx + offs_name]        ; save successor name
        push dword [edx + offs_score]       ; save successor score

        mov dword ecx, [edi + offs_name]
        mov dword [edx + offs_name], ecx    ; rewrite successor's name
        push word [edx + offs_id]
        push dword root
        call remove_id                      ; remove successor with node's name
        add esp, 6

        pop dword eax                       ; restore successor's score
        pop dword ecx                       ; restore successor's name
        pop word bx                         ; restore successor's id

        mov dword [edi + offs_score], eax   ; replace score with successor's
        mov dword [edi + offs_name], ecx    ; replace name with successor's
        mov word [edi + offs_id], bx        ; replace id with successor's

    eof:
        add esp, 8
        pop ebx
        pop esi
        pop edi
        pop ebp
        ret

