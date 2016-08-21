#ifndef __EMBOSS__H__
#define __EMBOSS__H__


void emboss(unsigned char* src,
            unsigned char* dst,
            int width,
            int height,
            int src_row_size,
            int dst_row_size);


void apply_emboss(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    emboss(
        src->data,
        dst->data,
        src->width,
        src->height,
        src->row_size,
        dst->row_size
    );
}


filter_result emboss_h(int arg, char** argv, virtual_image* vi) {
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_emboss);
}


#endif /// __EMBOSS__H__
