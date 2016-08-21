#ifndef __CONTRAST__H__
#define __CONTRAST__H__


void contrast(unsigned char* src,
              unsigned char* dst,
              int width,
              int height,
              int src_row_size,
              int dst_row_size,
              int contr);


void apply_contrast(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    short int contr = (short int)(atoi(argv[arg+1]));
    contrast(
        src->data,
        dst->data,
        dst->row_bytes,
        dst->height,
        src->row_size,
        dst->row_size,
        contr
    );
}


filter_result contrast_h(int arg, char** argv, virtual_image* vi) {
    int contr = atoi(argv[arg+1]);
    if (contr < -128 || contr > 128) {
        printf("Invalid contrast value: %d\n", contr);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_mono(arg, argv, vi, &apply_contrast);
}


#endif /// __CONTRAST__H__
