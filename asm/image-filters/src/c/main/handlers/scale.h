#ifndef __SCALE__H__
#define __SCALE__H__


void scale(unsigned char* src,
           int src_width,
           int src_height,
           int src_row_size,
           unsigned char* dst,
           int dst_width,
           int dst_height,
           int dst_row_size,
           float scale_x,
           float scale_y,
           int interpolation_method);


void apply_scale(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    float scale_x = (float)atof(argv[arg+1]);
    float scale_y = (float)atof(argv[arg+2]);
    int interpolation = atoi(argv[arg+3]);
    scale(
        src->data,
        src->width,
        src->height,
        src->row_size,
        dst->data,
        dst->width,
        dst->height,
        dst->row_size,
        scale_x,
        scale_y,
        interpolation
    );
}


filter_result scale_h(int arg, char** argv, virtual_image* vi) {
    float scale_x = (float)atof(argv[arg+1]);
    float scale_y = (float)atof(argv[arg+2]);
    int interpolation = atoi(argv[arg+3]);
    if (scale_x <= 0.0) {
        printf("Invalid horizontal scale value: %f\n", scale_x);
        return (filter_result) { .retval=1 };
    }
    if (scale_y <= 0.0) {
        printf("Invalid vertical scale value: %f\n", scale_y);
        return (filter_result) { .retval=1 };
    }
    if (interpolation != 1 && interpolation != 2) {
        printf("Invalid interpolation method: %d\n", interpolation);
        return (filter_result) { .retval=1 };
    }
    int width = vi->mono.width;
    int height = vi->mono.height;
    int new_w = (int)(width * scale_x);
    int new_h = (int)(height * scale_y);
    return filter_arg_customized(
        arg,
        argv,
        vi,
        new_w,
        new_h,
        vi->mono.channels,
        vi->mono.is_color,
        &apply_scale
    );
}


#endif /// __SCALE__H__
