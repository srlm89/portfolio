; int first(int first,     | second    |    ebp+12
;           int second)    | first     |    ebp+8
;                          | ret addr  |    ebp+4
;                          | ebp       |    ebp

%define first         ebp+8
%define second        ebp+12

global the_first

the_first:

    push ebp
    mov ebp, esp

    mov dword eax, [first]

    pop ebp
    ret
