;-------------------------------------------------------------------------------
; void interpolation_bilinear(float x,              | src_row_size  |  ebp+28
;                             float y,              | height        |  ebp+24
;                             unsigned char* src,   | width         |  ebp+20
;                             int width,            | src           |  ebp+16
;                             int height,           | y             |  ebp+12
;                             int src_row_size);    | x             |  ebp+8
;                                                   | dir ret       |  ebp+4
;                                                   | EBP           |
;
;   - It obtains the value from source image src in pixel (x,y) using bilinear
;     interpolation, storing the value in EAX[7:0]
;
;     C(x, bot, top) = (w + 1 - x) * bot + (x - w) * top    where w = floor(x)
;
;     B1(x, y) = C(x, SRC(w, z), SRC(w + 1, z))             where w = floor(x),
;     B2(x, y) = C(x, SRC(w, z + 1), SRC(w + 1, z + 1))           z = floor(y)
;     BI(x, y) = C(y, B1(x, y), B2(x, y))
;
;     However, to avoid losing precision we implement:
;
;     BI(x,y) = (1+w-x) * (1+z-y) * SRC(w  , z)   +
;               (x-w)   * (1+z-y) * SRC(w+1, z)   +
;               (1+w-x) * (y-z)   * SRC(w  , z+1) +
;               (x-w)   * (y-z)   * SRC(w+1, z+1)
;-------------------------------------------------------------------------------

extern read_image_byte

global interpolation_bilinear

%define pos_x           [ebp+8]
%define pos_y           [ebp+12]
%define src             [ebp+16]
%define width           [ebp+20]
%define height          [ebp+24]
%define src_row_size    [ebp+28]
%define pos_x_flr       [ebp-4]
%define pos_y_flr       [ebp-8]
%define pix_1_1         [ebp-12]
%define pix_1_2         [ebp-16]
%define pix_2_1         [ebp-20]
%define pix_2_2         [ebp-24]
%define biline_1        [ebp-28]
%define biline_2        [ebp-32]

max_color:              dd 0xff
min_color:              dd 0x00

interpolation_bilinear:

    push ebp
    mov ebp, esp
    sub esp, 32

    finit
    fld dword pos_y             ; st0 = y
    fld dword pos_x             ; st1 = y, st0 = x
    fisttp dword pos_x_flr      ; pos_x_flr <- floor(x) = w
    fisttp dword pos_y_flr      ; pos_y_flr <- floor(y) = z

    push dword src_row_size
    push dword height
    push dword width
    push dword src
    push dword pos_y_flr
    push dword pos_x_flr
    call read_image_byte
    mov dword pix_1_1, eax      ; reads image[w,z]

    inc dword [esp]
    call read_image_byte        ; reads image[w+1,z]
    mov dword pix_2_1, eax

    inc dword [esp+4]
    call read_image_byte        ; reads image[w+1,z+1]
    mov dword pix_2_2, eax

    dec dword [esp]
    call read_image_byte        ; reads image[w,z+1]
    mov dword pix_1_2, eax
    add esp, 24

    push dword pix_2_2
    push dword pix_2_1
    push dword pix_1_2
    push dword pix_1_1
    push dword pos_y_flr
    push dword pos_y
    push dword pos_x_flr
    push dword pos_x
    call interpolate_without_losing_precision

    add esp, 64
    pop ebp
    ret


%define interpol_x      [ebp+8]
%define interpol_w      [ebp+12]
%define interpol_y      [ebp+16]
%define interpol_z      [ebp+20]
%define interpol_1_1    [ebp+24]
%define interpol_1_2    [ebp+28]
%define interpol_2_1    [ebp+32]
%define interpol_2_2    [ebp+36]

interpolate_without_losing_precision:

    push ebp
    mov ebp, esp
    sub esp, 4

    fld1                        ; st0 = 1
    fiadd dword interpol_w      ; st0 = (1+w)
    fsub dword interpol_x       ; st0 = (1+w-x) = x_l

    fld1                        ; st1 = x_l, st0 = 1
    fiadd dword interpol_z      ; st1 = x_l, st0 = (1+z)
    fsub dword interpol_y       ; st1 = x_l, st0 = (1+z-y) = y_l

    fmulp                       ; st0 = x_l*y_l
    fimul dword interpol_1_1    ; st0 = 1@1

    fld dword interpol_x        ; st1 = 1@1, st0 = x
    fisub dword interpol_w      ; st1 = 1@1, st0 = (x-w) = x_u

    fld1                        ; st2 = 1@1, st1 = x_u, st0 = 1
    fiadd dword interpol_z      ; st2 = 1@1, st1 = x_u, st0 = (1+z)
    fsub dword interpol_y       ; st2 = 1@1, st1 = x_u, st0 = y_l
    fmulp                       ; st1 = 1@1, st0 = x_u*y_l
    fimul dword interpol_2_1    ; st1 = 1@1, st0 = 2@1
    faddp                       ; st0 = 1@1 + 2@1

    fld1                        ; st1 = 1@1 + 2@1, st0 = 1
    fiadd dword interpol_w      ; st1 = 1@1 + 2@1, st0 = (1+w)
    fsub dword interpol_x       ; st1 = 1@1 + 2@1, st0 = x_l

    fld dword interpol_y        ; st2 = 1@1 + 2@1, st1 = x_l, st0 = y
    fisub dword interpol_z      ; st2 = 1@1 + 2@1, st1 = x_l, st0 = (y-z) = y_u
    fmulp                       ; st1 = 1@1 + 2@1, st0 = x_l*y_u
    fimul dword interpol_1_2    ; st1 = 1@1 + 2@1, st0 = 1@2
    faddp                       ; st0 = 1@1 + 2@1 + 1@2

    fld dword interpol_x        ; st1 = 1@1 + 2@1 + 1@2, st0 = x
    fisub dword interpol_w      ; st1 = 1@1 + 2@1 + 1@2, st0 = x_u
    fld dword interpol_y        ; st2 = 1@1 + 2@1 + 1@2, st1 = x_u, st0 = y
    fisub dword interpol_z      ; st2 = 1@1 + 2@1 + 1@2, st1 = x_u, st0 = y_u
    fmulp                       ; st1 = 1@1 + 2@1 + 1@2, st0 = x_u*y_u
    fimul dword interpol_2_2    ; st1 = 1@1 + 2@1 + 1@2, st0 = 2@2
    faddp                       ; st0 = 1@1 + 2@1 + 1@2 + 2@2

    fistp dword [ebp-4]
    mov dword eax, [ebp-4]

    cmp dword eax, [max_color]
    cmovg dword eax, [max_color]
    cmp dword eax, [min_color]
    cmovl dword eax, [min_color]

    add esp, 4
    pop ebp
    ret

