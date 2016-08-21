#include "../util/each_block.h"

void pixelize_function(unsigned char* block, int width, int height) {
    int i;
    int accum = 0;
    int size = width * height;

    for (i = 0; i < size; i += 1) {
        accum += block[i];
    }

    int pixel = ((float) accum) / size;

    for (i = 0; i < size; i += 1) {
        block[i] = pixel;
    }
}


void pixelize(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int pixel_width,
        int pixel_height
    ) {
    each_block(
        src,
        dst,
        width,
        height,
        src_row_size,
        dst_row_size,
        pixel_width,
        pixel_height,
        &pixelize_function
    );
}

