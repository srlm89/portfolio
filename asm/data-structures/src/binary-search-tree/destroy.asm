; void destroy(node* tree);
;
; |      *tree       |    [ebp+8]
; |   ret address    |    [ebp+4]
; |       EBP        |    [ebp]

extern free
global destroy


section .data

%define offs_name  2                        ; name
%define offs_left  10                       ; node* left
%define offs_right 14                       ; node* right

%define node [ebp - 16]                     ; node struct address
%define name [ebp - 20]                     ; node name address
%define subtree_left [ebp - 24]             ; left subtree address
%define subtree_right [ebp - 28]            ; right subtree address


section .text

destroy:

    push ebp
    mov ebp, esp
    push edi
    push esi
    push ebx
    sub esp, 16
    
    mov dword eax, [ebp+8]
    mov node, eax

    cmp dword eax, 0
    je eof
    
    mov dword ecx, [eax + offs_name]
    mov dword name, ecx

    mov dword ecx, [eax + offs_left]
    mov dword subtree_left, ecx

    mov dword ecx, [eax + offs_right]
    mov dword subtree_right, ecx
    
    push dword subtree_left
    call destroy
    add esp, 4

    push dword subtree_right
    call destroy
    add esp, 4

    push dword name
    call free
    add esp, 4

    push dword node
    call free
    add esp, 4

    eof:
        add esp, 16
        pop ebx
        pop esi
        pop edi
        pop ebp
        ret

