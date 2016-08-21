#ifndef __EACH_BLOCK__H__
#define __EACH_BLOCK__H__

typedef void callback(unsigned char*, int, int);


void process_block(
        unsigned char* src,
        unsigned char* dst,
        int src_row_size,
        int dst_row_size,
        int width_min,
        int width_max,
        int height_min,
        int height_max,
        int block_width,
        int block_height,
        callback cb);


void each_block(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int block_width,
        int block_height,
        callback cb);

#endif /// __EACH_BLOCK__H__

