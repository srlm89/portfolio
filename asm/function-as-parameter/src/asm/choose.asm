; int choose(function,     | second    |    ebp+16
;            int first,    | first     |    ebp+12
;            int second)   | function  |    ebp+8
;                          | ret addr  |    ebp+4
;                          | ebp       |    ebp


%define function      ebp+8
%define first         ebp+12
%define second        ebp+16


global choose

choose:

    push ebp
    mov ebp, esp

    push dword [second]
    push dword [first]
    call [function]
    add esp, 8

    pop ebp
    ret
