#ifndef __ROTATION__H__
#define __ROTATION__H__


#define PI 3.141592653589793


void rotation(unsigned char* src,
              int src_width,
              int src_height,
              int src_row_size,
              unsigned char* dst,
              int dst_width,
              int dst_height,
              int dst_row_size,
              float angle,
              int interpolation_method);


void apply_rotation(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    float alpha = (float)(atof(argv[arg+1]));
    int interpolation = atoi(argv[arg+2]);
    double theta = (double)alpha * (double)PI / (double)180.0;
    rotation(
        src->data,
        src->width,
        src->height,
        src->row_size,
        dst->data,
        dst->width,
        dst->height,
        dst->row_size,
        (float)theta,
        interpolation
    );
}


filter_result rotation_h(int arg, char** argv, virtual_image* vi) {
    int interpolation = atoi(argv[arg+2]);
    float alpha = (float)(atof(argv[arg+1]));
    if (alpha < 0.0 || alpha > 360) {
        printf("Invalid angle value: %f\n", alpha);
        return (filter_result) { .retval=1 };
    }
    if (interpolation != 1 && interpolation != 2) {
        printf("Invalid interpolation method: %d\n", interpolation);
        return (filter_result) { .retval=1 };
    }
    int width = vi->mono.width;
    int height = vi->mono.height;
    double theta = (double)alpha * (double)PI / (double)180.0;
    int new_w = 2 + (int)(width*(fabs(cos(theta))) + height*(fabs(sin(theta))));
    int new_h = 2 + (int)(width*(fabs(sin(theta))) + height*(fabs(cos(theta))));
    return filter_arg_customized(
        arg,
        argv,
        vi,
        new_w,
        new_h,
        vi->mono.channels,
        vi->mono.is_color,
        &apply_rotation
    );
}


#endif /// __ROTATION__H__
