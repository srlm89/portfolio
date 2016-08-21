#ifndef __PIXELIZE__H__
#define __PIXELIZE__H__


void pixelize(unsigned char* src,
              unsigned char* dst,
              int width,
              int height,
              int src_row_size,
              int dst_row_size,
              int pixel_width,
              int pixel_height);


void apply_pixelize(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    int pixel_x = atoi(argv[arg+1]);
    int pixel_y = atoi(argv[arg+2]);
    pixelize(
        src->data,
        dst->data,
        dst->width,
        dst->height,
        src->row_size,
        dst->row_size,
        pixel_x,
        pixel_y
    );
}


filter_result pixelize_h(int arg, char** argv, virtual_image* vi) {
    int width = vi->mono.width;
    int height = vi->mono.height;
    int pixel_x = atoi(argv[arg+1]);
    int pixel_y = atoi(argv[arg+2]);
    if (pixel_x < 1 || pixel_x > width) {
        printf("Invalid pixel width: %d\n", pixel_x);
        return (filter_result) { .retval=1 };
    }
    if (pixel_y < 1 || pixel_y > height) {
        printf("Invalid pixel height: %d\n", pixel_y);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_pixelize);
}


#endif /// __PIXELIZE__H__
