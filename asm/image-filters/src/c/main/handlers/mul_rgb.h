#ifndef __MUL_RGB__H__
#define __MUL_RGB__H__


void mul_rgb(int width,
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
             float r,
             float g,
             float b);


filter_result mul_rgb_h(int arg, char** argv, virtual_image* vi) {
    float red = atof(argv[arg+1]);
    float green = atof(argv[arg+2]);
    float blue = atof(argv[arg+3]);
    if (red < 0.0 || red > 2.0) {
        printf("Invalid value for red channel: %f\n", red);
        return (filter_result) { .retval=1 };
    }
    if (green < 0.0 || green > 2.0) {
        printf("Invalid value for green channel: %f\n", green);
        return (filter_result) { .retval=1 };
    }
    if (blue < 0.0 || blue > 2.0) {
        printf("Invalid value for blue channel: %f\n", blue);
        return (filter_result) { .retval=1 };
    }
    int width = vi->mono.width;
    int height = vi->mono.height;
    virtual_image dst = create_virtual_image(width, height, 3);
    mul_rgb(
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


#endif /// __MUL_RGB__H__
