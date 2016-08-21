#ifndef __EACH_BYTE_KERNEL__H__
#define __EACH_BYTE_KERNEL__H__

typedef unsigned char callback(unsigned char*, int, int, int);


void each_byte_kernel(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int block_width,
        int block_height,
        callback cb);

#endif /// __EACH_BYTE_KERNEL__H__

