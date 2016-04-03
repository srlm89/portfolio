; void print_in_order(node* list, char* file);
;
; |       file       |    [ebp+12]
; |      *list       |    [ebp+8]
; |    ret address   |    [ebp+4]
; |       EBP        |    [ebp]

extern fopen, fclose, fprintf
global print_in_order


section .data

format    db "[ %d %f %s ]", 0              ; fprintf format
mode      db "a", 0                         ; fopen open mode
newline   db 10,0                           ; "\n" in bytes

%define offs_id    0                        ; id
%define offs_name  2                        ; name
%define offs_score 6                        ; score
%define offs_prev  10                       ; previous node address
%define offs_next  14                       ; next node address

%define head [ebp+8]                        ; list head address
%define file [ebp+12]                       ; file name address

section .text

print_in_order:

    push ebp
    mov ebp, esp
    push esi
    push edi
    push ebx

    mov dword ebx, head                     ; ebx <- next node address
    cmp dword ebx, 0
    je eof

    push dword mode
    push dword file
    call fopen
    add esp, 8
    cmp dword eax, 0
    je exit_with_error
    mov dword edi, eax                      ; edi <- file pointer

    iterative_print:
        cmp dword ebx, 0
        je new_line_and_close_file
        push dword [ebx + offs_next]        ; save next node address
        push dword [ebx + offs_name]        ; push node name
        fld dword [ebx + offs_score]        ; push node score
        sub esp, 8
        fstp qword [esp]
        mov word cx, [ebx + offs_id]        ; push node id
        cwde
        push dword ecx
        push dword format                   ; push fprintf format
        push dword edi                      ; push file pointer
        call fprintf
        add esp, 24
        pop dword ebx                       ; restore next node address
        jmp iterative_print

    new_line_and_close_file:
        push newline
        push dword edi
        call fprintf                        ; prints final new line
        call fclose                         ; closes file
        add esp, 8
        jmp eof

    exit_with_error:
        mov dword eax, 1

    eof:
        pop ebx
        pop edi
        pop esi
        pop ebp
        ret

