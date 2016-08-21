## Filters

----

### Geometric Filters

#### Image Translation

Grayscale   | Translation
:----------:|:-----------:
![][orig_gs]|![][gs_tran]

RGB Image    | Translation
:-----------:|:-----------:
![][orig_rgb]|![][rgb_tran]

#### Image Rotation

Grayscal e  | Rotation
:----------:|:-----------:
![][orig_gs]|![][gs_rot]

RGB Image    | Rotation
:-----------:|:-----------:
![][orig_rgb]|![][rgb_rot]

#### Image Shear

Grayscale   | Horizontal Shear
:----------:|:---------------:
![][orig_gs]|![][gs_shear_x]

Grayscale   | Vertical Shear
:----------:|:---------------:
![][orig_gs]|![][gs_shear_y]

RGB Image    | Horizontal Shear
:-----------:|:---------------:
![][orig_rgb]|![][rgb_shear_x]

RGB Image    | Vertical Shear
:-----------:|:--------------:
![][orig_rgb]|![][rgb_shear_y]

#### Image Scale

Grayscale   | Scaling
:----------:|:-----------:
![][orig_gs]|![][gs_scale]

RGB Image    | Scaling
:-----------:|:------------:
![][orig_rgb]|![][rgb_scale]

----

### Visual Filters

#### Grayscale (BT.601)

RGB Image    | Grayscale
:-----------:|:------------:
![][orig_rgb]|![][rgb_gs]

#### Grayscale (Mean)

RGB Image    | Grayscale
:-----------:|:--------------:
![][orig_rgb]|![][rgb_gs_mean]

#### Equalization

Unequalized   | Equalized
:------------:|:-----------:
![][uneq_gs]  |![][gs_eq]
![][hist_gs_1]|![][hist_gs_2]

Unequalized    | Equalized
:-------------:|:------------:
![][uneq_rgb]  |![][rgb_eq]
![][hist_rgb_1]|![][hist_rgb_2]

#### Color Inversion

Grayscale   | Inversion
:----------:|:-----------:
![][orig_gs]|![][gs_inv]

RGB Image    | Inversion
:-----------:|:------------:
![][orig_rgb]|![][rgb_inv]

#### Mean Blur

Grayscale   | Mean Blur
:----------:|:-----------:
![][orig_gs]|![][gs_mean]

RGB Image    | Mean Blur
:-----------:|:------------:
![][orig_rgb]|![][rgb_mean]

#### Gaussian Blur

Grayscale   | Gaussian Blur
:----------:|:------------:
![][orig_gs]|![][gs_gauss]

RGB Image    | Gaussian Blur
:-----------:|:-------------:
![][orig_rgb]|![][rgb_gauss]

#### Noise Removal (Median)

Noise       | Noise Removal
:----------:|:-----------:
![][dust_gs]|![][gs_noise]

Noise        | Noise Removal
:-----------:|:------------:
![][dust_rgb]|![][rgb_noise]

#### Sharpen Image (Laplace)

Grayscale   | Laplace
:----------:|:-----------:
![][orig_gs]|![][gs_laplace]

RGB Image    | Laplace
:-----------:|:------------:
![][orig_rgb]|![][rgb_laplace]

#### Emboss

Grayscale   | Emboss
:----------:|:-----------:
![][orig_gs]|![][gs_emboss]

RGB Image    | Emboss
:-----------:|:------------:
![][orig_rgb]|![][rgb_emboss]

#### Glow

Grayscale   | Glow
:----------:|:-----------:
![][orig_gs]|![][gs_glow]

RGB Image    | Glow
:-----------:|:------------:
![][orig_rgb]|![][rgb_glow]

#### Add RGB

Grayscale   | Add RGB
:----------:|:-------------:
![][orig_gs]|![][gs_add_rgb]

RGB Image    | Add RGB
:-----------:|:--------------:
![][orig_rgb]|![][rgb_add_rgb]

#### Multiply RGB

Grayscale   | Multiply RGB
:----------:|:-------------:
![][orig_gs]|![][gs_mul_rgb]

RGB Image    | Multiply RGB
:-----------:|:--------------:
![][orig_rgb]|![][rgb_mul_rgb]

#### Bluetone

Grayscale   | Bluetone
:----------:|:-----------:
![][orig_gs]|![][gs_blue]

RGB Image    | Bluetone
:-----------:|:------------:
![][orig_rgb]|![][rgb_blue]

#### Adjust Brightness

Grayscale   | Brightness
:----------:|:-----------:
![][orig_gs]|![][gs_br]

RGB Image    | Brightness
:-----------:|:------------:
![][orig_rgb]|![][rgb_br]

#### Adjust Contrast

Grayscale   | Contrast
:----------:|:-----------:
![][orig_gs]|![][gs_cont]

RGB Image    | Contrast
:-----------:|:------------:
![][orig_rgb]|![][rgb_cont]

#### Gamma Correction

Grayscale   | Gamma
:----------:|:-----------:
![][orig_gs]|![][gs_gamma]

RGB Image    | Gamma
:-----------:|:------------:
![][orig_rgb]|![][rgb_gamma]

#### Floyd Steinberg Dithering

Grayscale   | Dithering
:----------:|:-----------:
![][orig_gs]|![][gs_floyd]

RGB Image    | Dithering
:-----------:|:------------:
![][orig_rgb]|![][rgb_floyd]

#### Pixelize

Grayscale   | Pixelize
:----------:|:-----------:
![][orig_gs]|![][gs_pix]

RGB Image    | Pixelize
:-----------:|:------------:
![][orig_rgb]|![][rgb_pix]

#### Pixel Spreading

Grayscale   | Spread
:----------:|:-----------:
![][orig_gs]|![][gs_spread]

RGB Image    | Spread
:-----------:|:------------:
![][orig_rgb]|![][rgb_spread]

----

### Chained Filter Application

`../bin/imagepro pics/rango.png rgb/chain.png -cont -30 -glow -floyd -gauss`

Grayscale   | Chained
:----------:|:-----------:
![][orig_gs]|![][gs_chain]

`../bin/imagepro pics/eleph.png gs/chain.png -g 0.5 -rgb* 1.5 1.5 1.5 -gs`

RGB Image    | Chained
:-----------:|:------------:
![][orig_rgb]|![][rgb_chain]

----

## Dependencies

OpenCV2.3.1_install

```bash
$ sudo apt-get update
$ sudo apt-get install imagemagick
$ sudo apt-get install libcv-dev libhighgui-dev libcvaux-dev libopencv-dev
```

## References

- *Dreamland Fandasy Studios*: Programming
  - [Image Processing Algorithms Part 2: Error Diffusion](http://www.dfstudios.co.uk/articles/image-processing-algorithms-part-2/)
  - [Image Processing Algorithms Part 3: Greyscale Conversion](http://www.dfstudios.co.uk/articles/image-processing-algorithms-part-3/)
  - [Image Processing Algorithms Part 4: Brightness Adjustment](http://www.dfstudios.co.uk/articles/image-processing-algorithms-part-4/)
  - [Image Processing Algorithms Part 5: Contrast Adjustment](http://www.dfstudios.co.uk/articles/image-processing-algorithms-part-5/)
  - [Image Processing Algorithms Part 6: Gamma Correction](http://www.dfstudios.co.uk/articles/image-processing-algorithms-part-6/)

- *Procesamiento de Imagenes y Vision por Computador* (Gines Garcia Mateos)
  - [Tema 1. Adquisición y representación de imágenes.](http://dis.um.es/~ginesgm/files/doc/pav/tema1.pdf)
  - [Tema 2. Procesamiento global de imágenes.](http://dis.um.es/~ginesgm/files/doc/pav/tema2.pdf)
  - [Tema 3. Filtros y transformaciones locales.](http://dis.um.es/~ginesgm/files/doc/pav/tema3.pdf)
  - [Tema 4. Transformaciones geométricas.](http://dis.um.es/~ginesgm/files/doc/pav/tema4.pdf)
  - [Tema 5. Espacios de color y el dominio frecuencial.](http://dis.um.es/~ginesgm/files/doc/pav/tema5.pdf)
  - [Tema 6. Análisis de imágenes.](http://dis.um.es/~ginesgm/files/doc/pav/tema6.pdf)

- [*Practical PHP.* Chapter 11](http://www.tuxradar.com/practicalphp/11/0/0)

- [*RoboRealm vision for machines.*](http://www.roborealm.com/help/Convolution.php)

- [GIMP Documentation. Chapter 16 *Filters*](http://docs.gimp.org/en/filters.html)

- [JHLabs Java Image Processing.](http://www.jhlabs.com/ip/filters/)

- [Tech-Algorithm. Algorithm and Programming.](http://tech-algorithm.com/articles/boxfiltering/)

- [*Blurring the Line.* Jason Waltman.](http://www.jasonwaltman.com/thesis/introduction.html)

- [Lode's Computer Graphics Tutorial.](http://lodev.org/cgtutor/filtering.html)

- [Floyd-Steinberg Dithering. Stephen M. Omohundro.](omohundro.files.wordpress.com/2009/03/omohundro90_floyd_steinberg_dithering.pdf)


[gs_add_rgb]: md/gs/add_rgb.png "Add RGB (grayscale)"
[gs_blue]: md/gs/blue.png "Bluetone (grayscale)
[gs_br]: md/gs/br.png "Brightness (grayscale)"
[gs_cont]: md/gs/cont.png "Contrast (grayscale)"
[gs_emboss]: md/gs/emboss.png "Emboss (grayscale)"
[gs_eq]: md/gs/eq.png "Equalization (grayscale)"
[gs_floyd]: md/gs/floyd.png "Floyd Steinberg Dithering (grayscale)"
[gs_gamma]: md/gs/gamma.png "Gamma Correction (grayscale)"
[gs_gauss]: md/gs/gauss.png "Gaussian Blur (grayscale)"
[gs_glow]: md/gs/glow.png "Image Glow (grayscale)"
[gs_inv]: md/gs/inv.png "Color Inverstion (grayscale)"
[gs_laplace]: md/gs/laplace.png "Laplace Sharpening (grayscale)"
[gs_mean]: md/gs/mean.png "Mean Blur (grayscale)"
[gs_noise]: md/gs/noise.png "Median Noise Removal (grayscale)"
[gs_mul_rgb]: md/gs/mul_rgb.png "Multiply RGB (grayscale)"
[gs_pix]: md/gs/pix.png "Pixelize (grayscale)"
[gs_rot]: md/gs/rot.png "Rotation (grayscale)"
[gs_scale]: md/gs/scale.png "Scaling (grayscale)"
[gs_shear_x]: md/gs/shear_x.png "Horizontal Shear (grayscale)"
[gs_shear_y]: md/gs/shear_y.png "Vertical Shear (grayscale)"
[gs_spread]: md/gs/spread.png "Pixel Spreading (grayscale)"
[gs_tran]: md/gs/tran.png "Translation (grayscale)"
[gs_chain]: md/gs/chain.png "Chained Filter Application (grayscale)"
[uneq_gs]: md/gs/eq-in.png "Unequalized (grayscale)"
[orig_gs]: md/gs/original.png "Original (grayscale)"
[dust_gs]: md/gs/noise-in.png "Image with noise (grayscale)"

[rgb_add_rgb]: md/rgb/add_rgb.png "Add RGB (color image)"
[rgb_blue]: md/rgb/blue.png "Bluetone (grayscale)
[rgb_br]: md/rgb/br.png "Brightness (color image)"
[rgb_cont]: md/rgb/cont.png "Contrast (color image)"
[rgb_emboss]: md/rgb/emboss.png "Emboss (color image)"
[rgb_eq]: md/rgb/eq.png "Equalization (color image)"
[rgb_floyd]: md/rgb/floyd.png "Floyd Steinberg Dithering (color image)"
[rgb_gamma]: md/rgb/gamma.png "Gamma Correction (color image)"
[rgb_gauss]: md/rgb/gauss.png "Gaussian Blur (color image)"
[rgb_glow]: md/rgb/glow.png "Image Glow (color image)"
[rgb_gs]: md/rgb/gs.png "Grayscale conversion (BT.601)"
[rgb_gs_mean]: md/rgb/gs_mean.png "Grayscale conversion (mean)"
[rgb_inv]: md/rgb/inv.png "Color Inverstion (color image)"
[rgb_laplace]: md/rgb/laplace.png "Laplace Sharpening (color image)"
[rgb_mean]: md/rgb/mean.png "Mean Blur (color image)"
[rgb_noise]: md/rgb/noise.png "Median Noise Removal (color image)"
[rgb_mul_rgb]: md/rgb/mul_rgb.png "Multiply RGB (color image)"
[rgb_pix]: md/rgb/pix.png "Pixelize (color image)"
[rgb_rot]: md/rgb/rot.png "Rotation (color image)"
[rgb_scale]: md/rgb/scale.png "Scaling (color image)"
[rgb_shear_x]: md/rgb/shear_x.png "Horizontal Shear (color image)"
[rgb_shear_y]: md/rgb/shear_y.png "Vertical Shear (color image)"
[rgb_spread]: md/rgb/spread.png "Pixel Spreading (color image)"
[rgb_tran]: md/rgb/tran.png "Translation (color image)"
[rgb_chain]: md/rgb/chain.png "Chained Filter Application (color image)"
[uneq_rgb]: md/rgb/eq-in.png "Unequalized (color image)"
[orig_rgb]: md/rgb/original.png "Original (color image)"
[dust_rgb]: md/rgb/noise-in.png "Image with noise (color image)"

[hist_rgb_1]: md/histogram/rgb_uneq.png "Unequalized Color Image Histogram"
[hist_rgb_2]: md/histogram/rgb_eq.png "Equalized Color Image Histogram"
[hist_gs_1]: md/histogram/gs_uneq.png "Unequalized Grayscale Image Histogram"
[hist_gs_2]: md/histogram/gs_eq.png "Equalized Grayscale Image Histogram"


