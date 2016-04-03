; void print_in_order(node* tree, char *filename);
;
; |       file        |    [ebp+12]
; |      *tree        |    [ebp+8]
; |    ret address    |    [ebp+4]
; |       EBP         |    [ebp]

extern fopen, fprintf, fclose
global print_in_order


section .data

format    db "[ %d %f %s ]", 0              ; fprintf format
mode      db "a", 0                         ; fopen open mode
newline   db 10,0                           ; "\n" in bytes

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_left  10                       ; node* left
%define offs_right 14                       ; node* right

%define root [ebp+8]                        ; root address
%define file [ebp+12]                       ; file name address


section .text

print_in_order:

    push ebp
    mov ebp, esp
    push edi
    push esi
    push ebx

    push dword mode
    push dword file
    call fopen                              ; eax <- file pointer
    add dword esp, 8

    cmp dword eax, 0
    je end_with_error

    mov dword edi, eax                      ; edi <- file pointer
    push dword root
    call recursive_print
    add esp, 4

    push newline
    push dword edi
    call fprintf
    add esp, 8

    push dword edi
    call fclose
    add esp, 4
    jmp eof

    end_with_error:
        mov dword eax, 1
    
    eof:
        pop ebx
        pop esi
        pop edi
        pop ebp
        ret


recursive_print:

    push ebp
    mov ebp, esp
    push edi
    push esi
    push ebx

    mov dword ebx, [ebp + 8]                ; ebx <- node address
    cmp dword ebx, 0
    je end_recursive_print

    push dword [ebx + offs_left]
    call recursive_print                    ; print left subtree
    add esp, 4

    ; fprintf(file, "[ %d %f %s ]", tree->id, tree->score, tree->name)
    push dword [ebx + offs_name]            ; parameter: node name (char*)
    fld dword [ebx + offs_score]            ; parameter: node score (float)
    sub esp, 8
    fstp qword [esp]
    xor eax, eax                            ; parameter: node id (short)
    mov word ax, [ebx + offs_id]
    cwde                                    ; eax <- sign_extend(ax)
    push dword eax
    push dword format                       ; parameter: format (char*)
    push dword edi                          ; parameter: file (FILE*)
    call fprintf
    add esp, 24

    push dword [ebx + offs_right]
    call recursive_print                    ; print right subtree
    add esp, 4

    end_recursive_print:
        pop ebx
        pop esi
        pop edi
        pop ebp
        ret

