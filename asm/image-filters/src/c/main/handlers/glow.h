#ifndef __GLOW__H__
#define __GLOW__H__


void glow(unsigned char* src,
          unsigned char* dst,
          int width,
          int height,
          int src_row_size,
          int dst_row_size);


void apply_glow(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    glow(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result glow_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_glow);
}


#endif /// __GLOW__H__
