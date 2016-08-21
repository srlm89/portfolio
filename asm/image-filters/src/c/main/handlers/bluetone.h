#ifndef __BLUETONE__H__
#define __BLUETONE__H__


void bluetone(int width,
              int height,
              unsigned char* src_R,
              unsigned char* src_G,
              unsigned char* src_B,
              int src_R_row,
              int src_G_row,
              int src_B_row,
              unsigned char* dst_R,
              unsigned char* dst_G,
              unsigned char* dst_B,
              int dst_R_row,
              int dst_G_row,
              int dst_B_row);


filter_result bluetone_h(int arg, char** argv, virtual_image* vi) {
    int width = vi->mono.width;
    int height = vi->mono.height;
    virtual_image dst = create_virtual_image(width, height, 3);
    bluetone(
        width,
        height,
        vi->channel_red.data,
        vi->channel_green.data,
        vi->channel_blue.data,
        vi->channel_red.row_size,
        vi->channel_green.row_size,
        vi->channel_blue.row_size,
        dst.channel_red.data,
        dst.channel_green.data,
        dst.channel_blue.data,
        dst.channel_red.row_size,
        dst.channel_green.row_size,
        dst.channel_blue.row_size
    );
    return (filter_result) { 0, dst };
}


#endif /// __BLUETONE__H__
