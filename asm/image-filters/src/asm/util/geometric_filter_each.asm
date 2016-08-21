;-------------------------------------------------------------------------------
; void geometric_filter_each(             | method       |        ebp+48
;            unsigned char* src,          | src_y        |        ebp+44
;            int srcw,                    | src_x        |        ebp+40
;            int srch,                    | dst_row_size |        ebp+36
;            int src_row_size,            | dsth         |        ebp+32
;            unsigned char* dst,          | dstw         |        ebp+28
;            int dstw,                    | dst          |        ebp+24
;            int dsth,                    | src_row_size |        ebp+20
;            int dst_row_size,            | srch         |        ebp+16
;            function* src_x,             | srcw         |        ebp+12
;            function* src_y,             | src          |        ebp+8
;            int method);                 | dir ret      |        ebp+4
;                                         | EBP          |
;
;   - DST(x, y) = interpolate( SRC( src_x(x, y), src_y(x, y) ) )
;   - Doest not use ECX, EDX
;-------------------------------------------------------------------------------

extern interpolation_bilinear, interpolation_neighbor

global geometric_filter_each

%define src                     [ebp+8]
%define src_width               [ebp+12]
%define src_height              [ebp+16]
%define src_row_size            [ebp+20]
%define dst                     [ebp+24]
%define dst_width               [ebp+28]
%define dst_height              [ebp+32]
%define dst_row_size            [ebp+36]
%define src_x                   [ebp+40]
%define src_y                   [ebp+44]
%define interpolation           [ebp+48]
%define interpolation_method    [ebp-4]
%define float_x                 [ebp-8]
%define float_y                 [ebp-12]

geometric_filter_each:

    push ebp
    mov ebp, esp
    sub esp, 12
    push esi
    push edi
    push ebx

    mov dword edi, src              ; edi <- *src
    cmp dword edi, 0
    je exit

    mov dword edi, dst              ; edi <- *dst
    cmp dword edi, 0
    je exit

    mov dword esi, 0
    cmp dword interpolation, 1
    je set_interpolation_neighbor
    cmp dword interpolation, 2
    je set_interpolation_bilinear

    set_interpolation_neighbor:
        mov dword interpolation_method, interpolation_neighbor
        jmp cycle_rows

    set_interpolation_bilinear:
        mov dword interpolation_method, interpolation_bilinear
        jmp cycle_rows

    cycle_rows:

        xor ebx, ebx                ; ebx <- column counter
        cmp dword esi, dst_height   ; esi <- row counter
        je exit

        cycle_columns:

            push dword ebx
            push dword esi
            call src_x
            mov dword float_x, eax
            call src_y
            mov dword float_y, eax
            add esp, 8

            push dword src_row_size
            push dword src_height
            push dword src_width
            push dword src
            push dword float_y
            push dword float_x
            call interpolation_method
            add esp, 24

            mov byte [edi+ebx], al

            inc dword ebx
            cmp dword ebx, dst_width
            jl cycle_columns

        add dword edi, dst_row_size
        inc dword esi
        jmp cycle_rows

    exit:
        pop ebx
        pop edi
        pop esi
        add esp, 12
        pop ebp
        ret

