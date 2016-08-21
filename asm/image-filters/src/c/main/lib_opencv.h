#ifndef __LIB_OPENCV__H__
#define __LIB_OPENCV__H__


IplImage* open_image_color(char* src_image) {
    printf("Trying to load image (color) from: %s\n", src_image);
    IplImage* image = cvLoadImage(src_image, CV_LOAD_IMAGE_COLOR);
    if (!image) {
        printf("Failed to load image (color) from: %s\n", src_image);
    }
    return image;
}


IplImage* open_image_unchanged(char* src_image) {
    printf("Trying to load image (unchanged) from: %s\n", src_image);
    IplImage* image = cvLoadImage(src_image, CV_LOAD_IMAGE_UNCHANGED);
    if (image) {
        int channels = image->nChannels;
        if ((channels != 1) && (channels != 3)) {
            printf("Unexpectd number of channels: %d\n", channels);
            cvReleaseImage(&image);
            image = open_image_color(src_image);
        }
    }
    else {
        printf("Failed to load image (unchanged) from: %s\n", src_image);
    }
    return image;
}


int copy_image(char* src_image, char* dst_image) {
    if (strcmp(src_image, dst_image) == 0) {
        printf("Destination file cannot be source file\n");
        return 1;
    }

    IplImage* base = open_image_unchanged(src_image);
    if (! base) {
        return 1;
    }

    cvSaveImage(dst_image, base, NULL);
    cvReleaseImage(&base);
    return 0;
}


ipl_data read_ipl_data(IplImage* image) {
    ipl_data ipl = {
        .image     = image,
        .width     = image->width,
        .height    = image->height,
        .channels  = image->nChannels,
        .is_color  = (image->nChannels == 3),
        .row_bytes = image->width * image->nChannels,
        .row_size  = image->widthStep,
        .data      = (unsigned char*)(image->imageData)
    };
    return ipl;
}


void split_channels(virtual_image* vi) {
    if (vi != NULL) {
        IplImage* image = vi->mono.image;
        int uses_rgb = (image->nChannels == 3);
        if (uses_rgb) {
            IplImage* blue  = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            IplImage* green = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            IplImage* red   = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            cvSplit(image, blue, green, red, NULL);
            vi->channel_blue  = read_ipl_data(blue);
            vi->channel_green = read_ipl_data(green);
            vi->channel_red   = read_ipl_data(red);
        }
        vi->uses_rgb = uses_rgb;
    }
}


virtual_image load_virtual_image(char* image_path, int force_color) {
    virtual_image vi;
    IplImage* image;
    if (force_color) {
        image = open_image_color(image_path);
    }
    else {
        image = open_image_unchanged(image_path);
    }
    vi.mono = read_ipl_data(image);
    vi.path = image_path;
    split_channels(&vi);
    return vi;
}


virtual_image create_virtual_image_no_rgb(int width, int height, int channels) {
    virtual_image vi = { .uses_rgb = 0 };
    IplImage* ipl = cvCreateImage(cvSize(width,height), IPL_DEPTH_8U, channels);
    vi.mono = read_ipl_data(ipl);
    return vi;
}


virtual_image create_virtual_image(int width, int height, int channels) {
    virtual_image vi = create_virtual_image_no_rgb(width, height, channels);
    split_channels(&vi);
    return vi;
}


void release_virtual_image(virtual_image* vi) {
    if (vi != NULL) {
        if (vi->uses_rgb) {
            cvReleaseImage(&vi->channel_blue.image);
            cvReleaseImage(&vi->channel_green.image);
            cvReleaseImage(&vi->channel_red.image);
        }
        cvReleaseImage(&vi->mono.image);
    }
}


void save_virtual_image(char* out, virtual_image* vi) {
    if (vi != NULL) {
        if (vi->uses_rgb) {
            cvMerge(
                vi->channel_blue.image,
                vi->channel_green.image,
                vi->channel_red.image,
                NULL,
                vi->mono.image
            );
        }
        cvSaveImage(out, vi->mono.image, NULL);
    }
}


#endif /// __LIB_OPENCV__H__
