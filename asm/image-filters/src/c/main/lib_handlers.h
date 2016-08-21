#ifndef __LIB_HANDLERS__H__
#define __LIB_HANDLERS__H__


typedef void argfiltercall(int, char**, ipl_data*, ipl_data*);


filter_result filter_arg_customized(int arg,
                                    char** argv,
                                    virtual_image* vi,
                                    int width,
                                    int height,
                                    int channels,
                                    int apply_3_channels,
                                    argfiltercall apply) {
    virtual_image dst;
    if (apply_3_channels) {
        dst = create_virtual_image(width, height, channels);
        apply(arg, argv, &vi->channel_blue,  &dst.channel_blue);
        apply(arg, argv, &vi->channel_green, &dst.channel_green);
        apply(arg, argv, &vi->channel_red,   &dst.channel_red);
    }
    else {
        dst = create_virtual_image_no_rgb(width, height, channels);
        apply(arg, argv, &vi->mono, &dst.mono);
    }
    return (filter_result) { 0, dst };
}


filter_result filter_mono_to_gs(int arg,
                                char** argv,
                                virtual_image* vi,
                                argfiltercall apply) {
    int apply_3_channels = 0;
    int dst_image_channels = 1;
    return filter_arg_customized(
        arg,
        argv,
        vi,
        vi->mono.width,
        vi->mono.height,
        dst_image_channels,
        apply_3_channels,
        apply
    );
}


filter_result filter_arg_mono(int arg,
                              char** argv,
                              virtual_image* vi,
                              argfiltercall apply) {
    int apply_3_channels = 0;
    return filter_arg_customized(
        arg,
        argv,
        vi,
        vi->mono.width,
        vi->mono.height,
        vi->mono.channels,
        apply_3_channels,
        apply
    );
}


filter_result filter_arg_gs_or_rgb(int arg,
                                   char** argv,
                                   virtual_image* vi,
                                   argfiltercall apply) {
    return filter_arg_customized(
        arg,
        argv,
        vi,
        vi->mono.width,
        vi->mono.height,
        vi->mono.channels,
        vi->mono.is_color,
        apply
    );
}


#endif /// __LIB_HANDLERS__H__
