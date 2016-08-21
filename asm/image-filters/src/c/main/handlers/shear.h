#ifndef __SHEAR__H__
#define __SHEAR__H__


void shear(unsigned char* src,
           int src_width,
           int src_height,
           int src_row_size,
           unsigned char* dst,
           int dst_width,
           int dst_height,
           int dst_row_size,
           float shear_horizontal,
           float shear_vertical,
           int interpolation_method);


void apply_shear(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    char* opt = argv[arg+1];
    float f = (float)atof(argv[arg+2]);
    int interpolation = atoi(argv[arg+3]);
    shear(
        src->data,
        src->width,
        src->height,
        src->row_size,
        dst->data,
        dst->width,
        dst->height,
        dst->row_size,
        (strcmp("x", opt) == 0) ? f : 0.0f,
        (strcmp("y", opt) == 0) ? f : 0.0f,
        interpolation
    );
}


filter_result shear_h(int arg, char** argv, virtual_image* vi) {
    char* opt = argv[arg+1];
    float f = (float)atof(argv[arg+2]);
    int interpolation = atoi(argv[arg+3]);
    if (strcmp("x", opt) != 0 && strcmp("y", opt) != 0) {
        printf("Invalid orientation: %s\n", opt);
        return (filter_result) { .retval=1 };
    }
    if (f < -1.0 || f > 1.0) {
        printf("Invalid factor value: %f\n", f);
        return (filter_result) { .retval=1 };
    }
    if (interpolation != 1 && interpolation != 2) {
        printf("Invalid interpolation method: %d\n", interpolation);
        return (filter_result) { .retval=1 };
    }
    int width = vi->mono.width;
    int height = vi->mono.height;
    float i_x = (strcmp("x", opt) == 0) ? f : 0.0f;
    float i_y = (strcmp("y", opt) == 0) ? f : 0.0f;
    int new_w = (int)(width + fabs(i_x)*(float)(height));
    int new_h = (int)(height + fabs(i_y)*(float)(width));
    return filter_arg_customized(
        arg,
        argv,
        vi,
        new_w,
        new_h,
        vi->mono.channels,
        vi->mono.is_color,
        &apply_shear
    );
}


#endif /// __SHEAR__H__
