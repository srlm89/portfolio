#ifndef __STRUCTS__H__
#define __STRUCTS__H__


typedef struct {
    IplImage* image;
    int width;
    int height;
    int is_color;
    int channels;
    int row_bytes;
    int row_size;
    unsigned char* data;
} ipl_data;


typedef struct {
    char* path;
    int uses_rgb;
    ipl_data mono;
    ipl_data channel_blue;
    ipl_data channel_green;
    ipl_data channel_red;
} virtual_image;


typedef struct {
    int retval;
    virtual_image vi;
} filter_result;


typedef struct {
    char* name;
    int force_color;
    int skip_gray;
    int params;
    filter_result (*fun)(int, char**, virtual_image*);
} filter;


#endif /// __STRUCTS__H__
