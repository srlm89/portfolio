#ifndef __INVERSION__H__
#define __INVERSION__H__


void inversion(unsigned char* src,
               unsigned char* dst,
               int width,
               int height,
               int src_row_size,
               int dst_row_size);


void apply_inversion(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    inversion(
        src->data,
        dst->data,
        dst->row_bytes,
        dst->height,
        src->row_size,
        dst->row_size
    );
}


filter_result inversion_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_mono(arg, argv, vi, &apply_inversion);
}


#endif /// __INVERSION__H__
