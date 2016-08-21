#include <stdlib.h>
#include "each_block.h"

#define PROC_BLK(A, B, C, D, E, F, G) \
        process_block(src, dst, src_row_size, dst_row_size, A, B, C, D, E, F, G)

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
        callback cb) {
    int i;
    int j;
    int row;
    int col;
    unsigned char* block = malloc(block_height * block_width);
    for (row = height_min; row < height_max; row += block_height) {
        for (col = width_min; col < width_max; col += block_width) {
            for (i = 0; i < block_height; i += 1) {
                for (j = 0; j < block_width; j += 1) {
                    int src_index = (col + j) + (row + i) * src_row_size;
                    block[j + i*block_width] = src[src_index];
                }
            }
            cb(block, block_width, block_height);
            for (i = 0; i < block_height; i += 1) {
                for (j = 0; j < block_width; j += 1) {
                    int dst_index = (col + j) + (row + i) * dst_row_size;
                    dst[dst_index] = block[j + i*block_width];
                }
            }
        }
    }
    free(block);
}

void each_block(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int block_width,
        int block_height,
        callback cb
    ) {
    int div_x = width / block_width;
    int rem_x = width % block_width;
    int l_x = (1 + rem_x) / 2;
    int c_x = l_x + div_x * block_width;
    int r_x = rem_x / 2;

    int div_y = height / block_height;
    int rem_y = height % block_height;
    int l_y = (1 + rem_y) / 2;
    int c_y = l_y + div_y * block_height;
    int r_y = rem_y / 2;

    PROC_BLK(   0,   l_x,   0,    l_y,         l_x,          l_y, cb);
    PROC_BLK( l_x,   c_x,   0,    l_y, block_width,          l_y, cb);
    PROC_BLK( c_x, width,   0,    l_y,         r_x,          l_y, cb);

    PROC_BLK(   0,   l_x, l_y,    c_y,         l_x, block_height, cb);
    PROC_BLK( l_x,   c_x, l_y,    c_y, block_width, block_height, cb);
    PROC_BLK( c_x, width, l_y,    c_y,         r_x, block_height, cb);

    PROC_BLK(   0,   l_x, c_y, height,         l_x,          r_y, cb);
    PROC_BLK( l_x,   c_x, c_y, height, block_width,          r_y, cb);
    PROC_BLK( c_x, width, c_y, height,         r_x,          r_y, cb);
}

