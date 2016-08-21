;-------------------------------------------------------------------------------
; void gaussian_blur(unsigned char* src,     | dst_row_size  |        ebp+28
;                    unsigned char* dst,     | src_row_size  |        ebp+24
;                    int width,              | height        |        ebp+20
;                    int height,             | width         |        ebp+16
;                    int src_row_size,       | dst           |        ebp+12
;                    int dst_row_size);      | src           |        ebp+8
;                                            | dir ret       |        ebp+4
;                                            | EBP           |
;
;   - It uses this 3x3 kernel:
;
;      ----------------     -------------------
;      | x1 | x2 | x3 |     |  1  |  2  |  1  |
;      | x4 | x5 | x6 |  =  |  2  |  4  |  2  |       And divide by 16
;      | x7 | x8 | x9 |     |  1  |  2  |  1  |
;      ----------------     -------------------
;
;-------------------------------------------------------------------------------

extern each_16_bytes_3x3, proc_4_dwords_kernel

global gaussian_blur

%define src             [ebp+8]
%define dst             [ebp+12]
%define width           [ebp+16]
%define height          [ebp+20]
%define src_row_size    [ebp+24]
%define dst_row_size    [ebp+28]

kernel:    dd 0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000002 , 0x00000002 , 0x00000002 , 0x00000002, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000002 , 0x00000002 , 0x00000002 , 0x00000002, \
              0x00000004 , 0x00000004 , 0x00000004 , 0x00000004, \
              0x00000002 , 0x00000002 , 0x00000002 , 0x00000002, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000002 , 0x00000002 , 0x00000002 , 0x00000002, \
              0x00000001 , 0x00000001 , 0x00000001 , 0x00000001, \
              0x00000010 , 0x00000010 , 0x00000010 , 0x00000010

gaussian_blur:
    push ebp
    mov ebp, esp

    push dword gaussian_blur_function
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


gaussian_blur_function:
    push dword eax
    push dword kernel
    call proc_4_dwords_kernel
    add esp, 8
    ret

