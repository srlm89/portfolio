#ifndef __ADD_RGB__H__
#define __ADD_RGB__H__


void add_rgb(int width,
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
             int dst_B_row,
             int r,
             int g,
             int b);


filter_result add_rgb_h(int arg, char** argv, virtual_image* vi) {
    int red = atoi(argv[arg+1]);
    int green = atoi(argv[arg+2]);
    int blue = atoi(argv[arg+3]);
    if (red < -128 || red > 128) {
        printf("Invalid value for red channel: %d\n", red);
        return (filter_result) { .retval=1 };
    }
    if (green < -128 || green > 128) {
        printf("Invalid value for green channel: %d\n", green);
        return (filter_result) { .retval=1 };
    }
    if (blue < -128 || blue > 128) {
        printf("Invalid value for blue channel: %d\n", blue);
        return (filter_result) { .retval=1 };
    }
    int width = vi->mono.width;
    int height = vi->mono.height;
    virtual_image dst = create_virtual_image(width, height, 3);
    add_rgb(
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
        dst.channel_blue.row_size,
        red,
        green,
        blue
    );
    return (filter_result) { 0, dst };
}


#endif /// __ADD_RGB__H__
