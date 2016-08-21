#ifndef __LAPLACE__H__
#define __LAPLACE__H__


void laplace(unsigned char* src,
             unsigned char* dst,
             int width,
             int height,
             int src_row_size,
             int dst_row_size);


void apply_laplace(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    laplace(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result laplace_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_laplace);
}


#endif /// __LAPLACE__H__
