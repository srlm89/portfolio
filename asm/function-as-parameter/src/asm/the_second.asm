; int second(int first,    | second    |    ebp+12
;            int second)   | first     |    ebp+8
;                          | ret addr  |    ebp+4
;                          | ebp       |    ebp

%define first         ebp+8
%define second        ebp+12

global the_second

the_second:

    push ebp
    mov ebp, esp

    mov dword eax, [second]

    pop ebp
    ret
