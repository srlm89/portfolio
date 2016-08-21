;-------------------------------------------------------------------------------
; void read_image_byte(int x,                 | src_row_size  |  ebp+28
;                      int y,                 | height        |  ebp+24
;                      unsigned char* src,    | width         |  ebp+20
;                      int width,             | src           |  ebp+16
;                      int height,            | y             |  ebp+12
;                      int src_row_size);     | x             |  ebp+8
;                                             | dir ret       |  ebp+4
;                                             | EBP           |
;
;   - It obtains the value from source image src in pixel (x,y), storing the
;     value in EAX[7:0]. Returns zero ff the coordinate (x,y) is out of bounds.
;
;-------------------------------------------------------------------------------

global read_image_byte

%define pos_x           [ebp+8]
%define pos_y           [ebp+12]
%define src             [ebp+16]
%define width           [ebp+20]
%define height          [ebp+24]
%define src_row_size    [ebp+28]
%define offset          [ebp-4]

read_image_byte:

    push ebp
    mov ebp, esp
    sub esp, 4
    push esi

    xor eax, eax

    mov dword esi, width
    cmp dword pos_y, 0
    jl exit
    cmp dword pos_y, esi
    jge exit

    mov dword esi, height
    cmp dword pos_x, 0
    jl exit
    cmp dword pos_x, esi
    jge exit

    finit
    fild dword pos_y        ; st0 = y
    fild dword pos_x        ; st1 = y, st0 = x
    fild dword src_row_size ; st2 = y, st1 = x, st0 = src_row_size
    fmulp                   ; st1 = y, st0 = x*src_row_size
    faddp                   ; st0 = x*src_row_size + y
    fistp dword offset

    mov dword esi, src
    cmp dword esi, 0
    jz exit

    add dword esi, offset
    mov byte al, [esi]

    exit:
        pop esi
        add esp, 4
        pop ebp
        ret

