#ifndef __FLOYD_STEINBERG__H__
#define __FLOYD_STEINBERG__H__


void floyd_steinberg(unsigned char* src,
                     unsigned char* dst,
                     int width,
                     int height,
                     int src_row_size,
                     int dst_row_size);


void apply_floyd_steinberg(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    floyd_steinberg(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result floyd_steinberg_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_floyd_steinberg);
}


#endif /// __FLOYD_STEINBERG__H__
