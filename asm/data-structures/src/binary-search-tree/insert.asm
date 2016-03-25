; void insert(node** tree, char* name, short id, float score);
;
; |       score      |    [ebp+20]
; |        id        |    [ebp+16]
; |       name       |    [ebp+12]
; |     **tree       |    [ebp+8]
; |    ret address   |    [ebp+4]
; |       EBP        |    [ebp]

extern malloc, copy_string
global insert


section .data

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_left  10                       ; node* left
%define offs_right 14                       ; node* right
%define node_size  18                       ; size of node struct in bytes

%define root [ebp+8]                        ; root address
%define name [ebp+12]                       ; name address
%define id [ebp+16]                         ; id value
%define score [ebp+20]                      ; score value

%define insertion [ebp - 16]                ; parent insertion offset

section .text

insert:

    push ebp
    mov ebp, esp
    push edi
    push esi
    push ebx
    sub esp, 4

    mov dword esi, root
    mov dword edi, [esi]                    ; edi <- parent node address
    mov dword insertion, 0
    cmp dword edi, 0
    je new_node
    xor ecx, ecx

    binary_search:
        mov word cx, [edi + offs_id]
        mov dword esi, edi
        cmp word cx, id
        jg move_left
        jl move_right
        je eof

    move_left:
        mov dword insertion, offs_left
        mov dword edi, [edi + offs_left]
        cmp dword edi, 0
        je new_node
        jmp binary_search

    move_right:
        mov dword insertion, offs_right
        mov dword edi, [edi + offs_right]
        cmp dword edi, 0
        je new_node
        jmp binary_search

    new_node:
        push dword node_size
        call malloc                         ; eax <- node struct address
        add esp, 4
        mov ecx, insertion
        mov dword [esi + ecx], eax          ; link parent with new node
        mov word cx, id
        mov word  [eax + offs_id], cx       ; set new node id
        mov dword ecx, score
        mov dword [eax + offs_score], ecx   ; set new node score
        mov dword [eax + offs_left], 0      ; set left subtree to NULL
        mov dword [eax + offs_right], 0     ; set right subtree to NULL

        push eax
        push dword name
        call copy_string
        add esp, 4
        mov ecx, eax                        ; ecx <- copied buffer address
        pop eax                             ; eax <- node struct address
        mov dword [eax + offs_name], ecx

    eof:
        add esp, 4
        pop ebx
        pop esi
        pop edi
        pop ebp
        ret

