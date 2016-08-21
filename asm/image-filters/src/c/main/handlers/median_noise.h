#ifndef __MEDIAN_NOISE__H__
#define __MEDIAN_NOISE__H__


void median_noise(unsigned char* src,
                  unsigned char* dst,
                  int width,
                  int height,
                  int src_row_size,
                  int dst_row_size);


void apply_median_noise(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    median_noise(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result median_noise_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_median_noise);
}


#endif /// __MEDIAN_NOISE__H__
