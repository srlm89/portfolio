#ifndef __SPREAD__H__
#define __SPREAD__H__


void spread(unsigned char* src,
            unsigned char* dst,
            int width,
            int height,
            int src_row_size,
            int dst_row_size,
            int is_color_image,
            int range_width,
            int range_height);


void initialize_rand_seed(int value) {
    if (value == 0) {
        value = (unsigned) time(NULL);
    }
    srand(value);
}

void apply_spread(int arg, char** argv, ipl_data* src, ipl_data* dst) {
    int range_width = atoi(argv[arg+1]);
    int range_height = atoi(argv[arg+2]);
    int rand_seed = atoi(argv[arg+3]);
    initialize_rand_seed(rand_seed);
    spread(
        src->data,
        dst->data,
        dst->width,
        dst->height,
        src->row_size,
        dst->row_size,
        src->is_color,
        range_width,
        range_height
    );
}


filter_result spread_h(int arg, char** argv, virtual_image* vi) {
    int width = vi->mono.width;
    int height = vi->mono.height;
    int range_x = atoi(argv[arg+1]);
    int range_y = atoi(argv[arg+2]);
    if (range_x < 1 || range_x > width) {
        printf("Invalid range width: %d\n", range_x);
        return (filter_result) { .retval=1 };
    }
    if (range_y < 1 || range_y > height) {
        printf("Invalid range height: %d\n", range_y);
        return (filter_result) { .retval=1 };
    }
    return filter_arg_gs_or_rgb(arg, argv, vi, &apply_spread);
}


#endif /// __SPREAD__H__
