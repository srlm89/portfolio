#ifndef __MEAN_BLUR__H__
#define __MEAN_BLUR__H__


void mean_blur(unsigned char* src,
               unsigned char* dst,
               int width,
               int height,
               int src_row_size,
               int dst_row_size);


void apply_mean_blur(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    mean_blur(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result mean_blur_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_mean_blur);
}


#endif /// __MEAN_BLUR__H__
