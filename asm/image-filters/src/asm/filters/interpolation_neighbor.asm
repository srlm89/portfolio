;-------------------------------------------------------------------------------
; void interpolation_neighbor(float x,              | src_row_size  |  ebp+28
;                             float y,              | height        |  ebp+24
;                             unsigned char* src,   | width         |  ebp+20
;                             int width,            | src           |  ebp+16
;                             int height,           | y             |  ebp+12
;                             int src_row_size);    | x             |  ebp+8
;                                                   | dir ret       |  ebp+4
;                                                   | EBP           |
;
;   - It obtains the value from source image src in pixel (x,y) using neighbor
;     interpolation, storing the value in EAX[7:0]
;
;     f(x,y) = SRC( round_int(x), round_int(y) )
;-------------------------------------------------------------------------------

extern read_image_byte

global interpolation_neighbor

%define pos_x           [ebp+8]
%define pos_y           [ebp+12]
%define src             [ebp+16]
%define width           [ebp+20]
%define height          [ebp+24]
%define src_row_size    [ebp+28]
%define src_x           [ebp-4]
%define src_y           [ebp-8]

interpolation_neighbor:

    push ebp
    mov ebp, esp
    sub esp, 8

    finit
    fld dword pos_x             ; st0 = x
    frndint                     ; st0 = rnd(x)
    fistp dword src_x           ; src_x <- rnd(x)

    fld dword pos_y             ; st0 = y
    frndint                     ; st0 = rnd(y)
    fistp dword src_y           ; src_y <- rnd(y)

    push dword src_row_size
    push dword height
    push dword width
    push dword src
    push dword src_y
    push dword src_x
    call read_image_byte

    add esp, 32
    pop ebp
    ret

