;-------------------------------------------------------------------------------
; void emboss(unsigned char* src,     | dst_row_size  |        ebp+28
;             unsigned char* dst,     | src_row_size  |        ebp+24
;             int width,              | height        |        ebp+20
;             int height,             | width         |        ebp+16
;             int src_row_size,       | dst           |        ebp+12
;             int dst_row_size);      | src           |        ebp+8
;                                     | dir ret       |        ebp+4
;                                     | EBP           |
;
;   - It uses this 3x3 kernel:
;
;      ----------------     -------------------
;      | x1 | x2 | x3 |     |  -2 |  -1 |  0  |
;      | x4 | x5 | x6 |  =  |  -1 |  1  |  1  |       And divide by 1
;      | x7 | x8 | x9 |     |  0  |  1  |  2  |
;      ----------------     -------------------
;
;-------------------------------------------------------------------------------

extern each_16_bytes_3x3, proc_4_dwords_kernel

global emboss

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]

kernel:    dd 0xfffffffe , 0xfffffffe , 0xfffffffe , 0xfffffffe, \
              0xffffffff , 0xffffffff , 0xffffffff , 0xffffffff, \
              0x00000000 , 0x00000000 , 0x00000000 , 0x00000000, \
              0xffffffff , 0xffffffff , 0xffffffff , 0xffffffff, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000000 , 0x00000000 , 0x00000000 , 0x00000000, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000002 , 0x00000002 , 0x00000002 , 0x00000002, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001

emboss:
    push ebp
    mov ebp, esp

    push dword emboss_function
    push dword dst_row_size
    push dword src_row_size
    push dword height
    push dword width
    push dword dst
    push dword src
    call each_16_bytes_3x3
    add esp, 28

    pop ebp
    ret


emboss_function:
    push dword eax
    push dword kernel
    call proc_4_dwords_kernel
    add esp, 8
    ret

