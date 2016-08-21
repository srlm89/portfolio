#ifndef __GRAYSCALE__H__
#define __GRAYSCALE__H__


void grayscale(int width,
               int height,
               unsigned char* src_R,
               unsigned char* src_G,
               unsigned char* src_B,
               unsigned char* dst,
               int src_R_row,
               int src_G_row,
               int src_B_row,
               int dst_row_size);


filter_result grayscale_h(int arg, char** argv, virtual_image* vi) {
    int width  = vi->mono.width;
    int height = vi->mono.height;
    virtual_image dst = create_virtual_image_no_rgb(width, height, 1);
    grayscale(
        width,
        height,
        vi->channel_red.data,
        vi->channel_green.data,
        vi->channel_blue.data,
        dst.mono.data,
        vi->channel_red.row_size,
        vi->channel_green.row_size,
        vi->channel_blue.row_size,
        dst.mono.row_size
    );
    return (filter_result) { 0, dst };
}


#endif /// __GRAYSCALE__H__
