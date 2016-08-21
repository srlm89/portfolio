#ifndef __EQUALIZATION__H__
#define __EQUALIZATION__H__


void equalization(unsigned char* src,
                  unsigned char* dst,
                  int width,
                  int height,
                  int src_row_size,
                  int dst_row_size);


void apply_equalization(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    equalization(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result equalization_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_equalization);
}


#endif /// __EQUALIZATION__H__
