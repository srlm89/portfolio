#ifndef __GAMMA_CORRECTION__H__
#define __GAMMA_CORRECTION__H__

void gamma_correction(unsigned char* src,
                      unsigned char* dst,
                      int width,
                      int height,
                      int src_row_size,
                      int dst_row_size,
                      float gamma);


void apply_gamma(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    float gamma = (float)(atof(argv[arg+1]));
    gamma_correction(
        src->data,
        dst->data,
        dst->row_bytes,
        dst->height,
        src->row_size,
        dst->row_size,
        gamma
    );
}


filter_result gamma_correction_h(int arg, char** argv, virtual_image* vi) {
    float gamma = (float)(atof(argv[arg+1]));
    if (gamma < 0.1 || gamma > 5.00) {
        printf("Invalid gamma value: %f\n", gamma);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_mono(arg, argv, vi, &apply_gamma);
}


#endif /// __GAMMA_CORRECTION__H__
