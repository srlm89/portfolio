; int first(int first,     | second    |    ebp+12
;           int second)    | first     |    ebp+8
;                          | ret addr  |    ebp+4
;                          | ebp       |    ebp

%define first         ebp+8
%define second        ebp+12

extern choose, the_first
global choose_first

choose_first:

    push ebp
    mov ebp, esp

    push dword [second]
    push dword [first]
    push the_first
    call choose
    add esp, 12

    pop ebp
    ret
