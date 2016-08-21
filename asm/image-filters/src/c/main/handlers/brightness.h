#ifndef __BRIGHTNESS__H__
#define __BRIGHTNESS__H__


void brightness(unsigned char* src,
                unsigned char* dst,
                int width,
                int height,
                int src_row_size,
                int dst_row_size,
                short int bril);


void apply_brightness(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    short int bright = (short int)(atoi(argv[arg+1]));
    brightness(
        src->data,
        dst->data,
        dst->row_bytes,
        dst->height,
        src->row_size,
        dst->row_size,
        bright
    );
}


filter_result brightness_h(int arg, char** argv, virtual_image* vi) {
    int bright = atoi(argv[arg+1]);
    if (bright < -128 || bright > 128) {
        printf("Invalid brightness value: %d\n", bright);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_mono(arg, argv, vi, &apply_brightness);
}


#endif /// __BRIGHTNESS__H__
