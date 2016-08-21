#ifndef __GAUSSIAN_BLUR__H__
#define __GAUSSIAN_BLUR__H__


void gaussian_blur(unsigned char* src,
                   unsigned char* dst,
                   int width,
                   int height,
                   int src_row_size,
                   int dst_row_size);


void apply_gaussian_blur(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    gaussian_blur(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result gaussian_blur_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_gaussian_blur);
}


#endif /// __GAUSSIAN_BLUR__H__
