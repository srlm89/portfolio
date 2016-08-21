#ifndef __HELP__H__
#define __HELP__H__


int show_usage() {
    printf("                                                                                    \n");
    printf("    ./imagepro <input-image> <output-image> [option option_parameters]+             \n");
    printf("For instance:                                                                       \n");
    printf("    ./imagepro picture.png rotated.png -r 90 2                                      \n");
    printf("        This command rotates the input image 'picture.png', by 90 degrees and       \n");
    printf("        with bilinear interpolation and stores the result in a newly created        \n");
    printf("        output image 'rotated.png'                                                  \n");
    printf("                                                                                    \n");
    printf("For help use:                                                                       \n");
    printf("    ./imagepro -h                                                                   \n");
    printf("                                                                                    \n");
    return 1;
}


int show_help() {
    printf("                                                                                    \n");
    printf("./imagepro <input-image> <output-image> [option option_parameters]+                 \n");
    printf("                                                                                    \n");
    printf("Operation  Parameters                    Description                                \n");
    printf("------------------------------------------------------------------------------------\n");
    printf("-gs        N/A                           Grayscale Conversion (BT.601)              \n");
    printf("                                                                                    \n");
    printf("-gsm       N/A                           Grayscale Conversion (Mean)                \n");
    printf("                                                                                    \n");
    printf("-eq        N/A                           Histogram Equalization                     \n");
    printf("                                                                                    \n");
    printf("-invert    N/A                           Color Inversion                            \n");
    printf("                                                                                    \n");
    printf("-floyd     N/A                           Floyd–Steinberg Dithering                  \n");
    printf("                                                                                    \n");
    printf("-mean      N/A                           Image Blur (mean values)                   \n");
    printf("                                                                                    \n");
    printf("-gauss     N/A                           Image Blur (Gaussian)                      \n");
    printf("                                                                                    \n");
    printf("-noise     N/A                           Image Noise Removal (median values)        \n");
    printf("                                                                                    \n");
    printf("-laplace   N/A                           Image Sharpening (Laplace)                 \n");
    printf("                                                                                    \n");
    printf("-emboss    N/A                           Image Emboss                               \n");
    printf("                                                                                    \n");
    printf("-blue      N/A                           Image Bluetone                             \n");
    printf("                                                                                    \n");
    printf("-glow      N/A                           Image Glow                                 \n");
    printf("                                                                                    \n");
    printf("-br        Integer b (-128 <= b <= 128)  Change the bright of the image by #b       \n");
    printf("                                                                                    \n");
    printf("-cont      Integer c (-128 <= c <= 128)  Change the contrast of the image by #c     \n");
    printf("                                                                                    \n");
    printf("-g         Float g (0.1 <= g <= 5.0)     Gamma correction by a factor of #g         \n");
    printf("                                                                                    \n");
    printf("-t         Integer x                     Move the image within the canvas #x pixels \n");
    printf("           Integer y                     horizontally and #y pixels vertically      \n");
    printf("                                                                                    \n");
    printf("-pix       Integer x (1 <= x)            Pixelize image using pixel size equal to   \n");
    printf("           Integer y (1 <= y)            x in width and y in height                 \n");
    printf("                                                                                    \n");
    printf("-spr       Integer x (0 <= x)            Spread pixels with seed #s with range #x   \n");
    printf("           Integer y (0 <= y)            horizontally and range #y vertically       \n");
    printf("           Integer s (rand seed)                                                    \n");
    printf("                                                                                    \n");
    printf("-r         Float r (0.0 <= r <= 360.0)   Rotate the pictures by an angle of #r      \n");
    printf("           Integer i (1 or 2)            degrees with interpolation method #i       \n");
    printf("                                                                                    \n");
    printf("-rgb+      Integer r (-128 <= r <= 128)  Adds #r to Red channel, #g to Green channel\n");
    printf("           Integer g (-128 <= r <= 128)  and #b to Blue channel                     \n");
    printf("           Integer b (-128 <= r <= 128)                                             \n");
    printf("                                                                                    \n");
    printf("-rgb*      Float r (0.0 <= r <= 2.0)     Multiplies #r to Red channel, #g to Green \n");
    printf("           Float g (0.0 <= g <= 2.0)     channel and #b to Blue Channel.            \n");
    printf("           Float b (0.0 <= b <= 2.0)                                                \n");
    printf("                                                                                    \n");
    printf("-shear     String opt ('x' or 'y')       Shear image horizontally (if #opt is 'x')  \n");
    printf("           Float f (-1.0 <= f <= 1.0)    or vertically (if #opt is 'y') by a factor \n");
    printf("           Integer i (1 or 2)            of #f with interpolation method #i         \n");
    printf("                                                                                    \n");
    printf("-scale     Float x (x > 0.0)             Scale the image by a factor of #x          \n");
    printf("           Float y (y > 0.0)             horizontally and #y vertically with        \n");
    printf("           Integer i (1 or 2)            interpolation method #i                    \n");
    printf("                                                                                    \n");
    printf("Interpolation methods:                                                              \n");
    printf("    1   Nearest neighbor                                                            \n");
    printf("    2   Bilinear                                                                    \n");
    printf("                                                                                    \n");
    return 0;
}

#endif /// __HELP__H__
