#include <stdlib.h>
#include "../util/each_byte_kernel.h"

#define RANDMAX 2147483647.0

int random_int(int min, int max){
    int range = (max - min) + 1;
    int random_integer = min + (int)((double)range*rand() / (RANDMAX + 1.0));
    return random_integer;
}


unsigned char spread_function(unsigned char* block, int size, int rw, int rh) {
    int index = random_int(0, size-1);
    return block[index];
}


void spread(
        unsigned char* src,
        unsigned char* dst,
        int width,
        int height,
        int src_row_size,
        int dst_row_size,
        int is_color_image,
        int range_width,
        int range_height
    ){
    each_byte_kernel(
        src,
        dst,
        width,
        height,
        src_row_size,
        dst_row_size,
        range_width,
        range_height,
        &spread_function
    );
}

