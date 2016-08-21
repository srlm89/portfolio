#include <opencv/highgui.h>
#include <stdio.h>
#include <time.h>
#include "help.h"
#include "structs.h"
#include "lib_opencv.h"
#include "lib_handlers.h"
#include "handlers/add_rgb.h"
#include "handlers/bluetone.h"
#include "handlers/brightness.h"
#include "handlers/contrast.h"
#include "handlers/emboss.h"
#include "handlers/equalization.h"
#include "handlers/floyd_steinberg.h"
#include "handlers/gamma_correction.h"
#include "handlers/gaussian_blur.h"
#include "handlers/glow.h"
#include "handlers/grayscale.h"
#include "handlers/grayscale_mean.h"
#include "handlers/inversion.h"
#include "handlers/laplace.h"
#include "handlers/mean_blur.h"
#include "handlers/median_noise.h"
#include "handlers/mul_rgb.h"
#include "handlers/pixelize.h"
#include "handlers/rotation.h"
#include "handlers/scale.h"
#include "handlers/shear.h"
#include "handlers/spread.h"
#include "handlers/translation.h"


filter filters[] = {
    { .fun=&grayscale_h,        .name="-gs",      .params=0, .skip_gray=1 },
    { .fun=&grayscale_mean_h,   .name="-gsm",     .params=0, .skip_gray=1 },
    { .fun=&equalization_h,     .name="-eq",      .params=0 },
    { .fun=&inversion_h,        .name="-invert",  .params=0 },
    { .fun=&floyd_steinberg_h,  .name="-floyd",   .params=0 },
    { .fun=&mean_blur_h,        .name="-mean",    .params=0 },
    { .fun=&gaussian_blur_h,    .name="-gauss",   .params=0 },
    { .fun=&median_noise_h,     .name="-noise",   .params=0 },
    { .fun=&laplace_h,          .name="-laplace", .params=0 },
    { .fun=&emboss_h,           .name="-emboss",  .params=0 },
    { .fun=&glow_h,             .name="-glow",    .params=0 },
    { .fun=&bluetone_h,         .name="-blue",    .params=0, .force_color = 1 },
    { .fun=&brightness_h,       .name="-br",      .params=1 },
    { .fun=&contrast_h,         .name="-cont",    .params=1 },
    { .fun=&gamma_correction_h, .name="-g",       .params=1 },
    { .fun=&translation_h,      .name="-t",       .params=2 },
    { .fun=&rotation_h,         .name="-r",       .params=2 },
    { .fun=&pixelize_h,         .name="-pix",     .params=2 },
    { .fun=&spread_h,           .name="-spr",     .params=3 },
    { .fun=&shear_h,            .name="-shear",   .params=3 },
    { .fun=&scale_h,            .name="-scale",   .params=3 },
    { .fun=&add_rgb_h,          .name="-rgb+",    .params=3, .force_color = 1 },
    { .fun=&mul_rgb_h,          .name="-rgb*",    .params=3, .force_color = 1 },
    { .name = "unk" }
};


int main(int argc, char* argv[]) {

    if (argc < 3) {
        if (argc == 2 && (strcmp(argv[1], "-h") == 0)) {
            return show_help();
        }
        return show_usage();
    }

    char* src_image = argv[1];
    char* dst_image = argv[2];
    int errval = argc + 1;
    int arg = 3;

    if (copy_image(src_image, dst_image)) {
        return 1;
    }

    while (arg < argc) {
        int f = -1;
        while (1) {
            f += 1;
            char* opt = argv[arg];
            filter fi = filters[f];
            if (strcmp(fi.name, "unk") == 0) {
                printf("Error: unknown option '%s'\n", opt);
                arg = errval;
                break;
            }
            if (strcmp(fi.name, opt) == 0) {
                virtual_image vi = load_virtual_image(dst_image,fi.force_color);
                if (arg + fi.params >= argc) {
                    printf("Error: '%s' needs %d arguments\n", opt, fi.params);
                    arg = errval;
                }
                else if (! vi.mono.is_color && fi.skip_gray) {
                    printf("Warning: '%s' does not support gray scale\n", opt);
                }
                else {
                    filter_result result = fi.fun(arg, argv, &vi);
                    if (result.retval != 0) {
                        arg = errval;
                    }
                    else {
                        save_virtual_image(dst_image, &result.vi);
                        release_virtual_image(&result.vi);
                    }
                }
                release_virtual_image(&vi);
                arg += fi.params + 1;
                break;
            }
        }
    }

    if (arg > argc) {
        printf("An error occured due to invalid arguments\n");
    }

    return 0;
}

