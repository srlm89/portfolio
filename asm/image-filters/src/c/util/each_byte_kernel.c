#include <stdlib.h>
#include "each_byte_kernel.h"


void fill_kernel(
    unsigned char* src,
    int src_row_size,
    int width,
    int height,
    unsigned char* kernel,
    int range_width,
    int range_height,
    int row,
    int col) {

    int r;
    int c;
    int i = 0;
    int in_bounds_x;
    int in_bounds_y;

    for (r = -range_height; r <= range_height; r += 1) {
        for (c = -range_width; c <= range_width; c += 1) {
            in_bounds_x = (col + c) > 0 && (col + c) < width;
            in_bounds_y = (row + r) > 0 && (row + r) < height;
            if (in_bounds_x && in_bounds_y) {
                kernel[i] = src[(col + c) + (row + r)*src_row_size];
            }
            else {
                kernel[i] = 0;
            }
            i += 1;
        }
    }
}

void each_byte_kernel(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int range_width,
        int range_height,
        callback cb) {

    int row;
    int col;
    unsigned char pixel;
    int size = (2 * range_height + 1) * (2 * range_width + 1);
    unsigned char* kernel = malloc(size);
    for (row = 0; row < height; row += 1) {
        for (col = 0; col < width; col += 1) {
            fill_kernel(src,
                        src_row_size,
                        width,
                        height,
                        kernel,
                        range_width,
                        range_height,
                        row,
                        col);
            pixel = cb(kernel, size, range_width, range_height);
            dst[col + row*dst_row_size] = pixel;
        }
    }
    free(kernel);
}
