#ifndef __TRANSLATION__H__
#define __TRANSLATION__H__


void translation(unsigned char* src,
                 unsigned char* dst,
                 int width,
                 int height,
                 int src_row_size,
                 int dst_row_size,
                 int move_horizontal,
                 int move_vertical);


void apply_translation(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    int axis_x = atoi(argv[arg+1]);
    int axis_y = atoi(argv[arg+2]);
    translation(
        src->data,
        dst->data,
        dst->width,
        dst->height,
        src->row_size,
        dst->row_size,
        axis_x,
        axis_y
    );
}


filter_result translation_h(int arg, char** argv, virtual_image* vi) {
    int axis_x = atoi(argv[arg+1]);
    int axis_y = atoi(argv[arg+2]);
    int width = vi->mono.width;
    int height = vi->mono.height;
    if (axis_x < -width || axis_x > width) {
        printf("Width value %d out of bounds (%d)\n", axis_x, width);
        return (filter_result) { .retval=1 };
    }
    if (axis_y < -height || axis_y > height) {
        printf("Height value %d out of bounds (%d)\n", axis_y, height);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_translation);
}


#endif /// __TRANSLATION__H__
