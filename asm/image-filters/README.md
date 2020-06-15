## Geometric Filters

#### Image Translation

`../bin/imagepro pics/eleph.png gs/tran.png -t 30 -40`

Grayscale   | Translation
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/tran.png)

`../bin/imagepro pics/rango.png rgb/tran.png -t -60 35`

RGB Image    | Translation
:-----------:|:-----------:
![](md/rgb/original.png)|![](md/rgb/tran.png)

#### Image Rotation

`../bin/imagepro pics/eleph.png gs/rot.png -r 45 1`

Grayscale   | Rotation
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/rot.png)

`../bin/imagepro pics/rango.png rgb/rot.png -r 60 2`

RGB Image    | Rotation
:-----------:|:-----------:
![](md/rgb/original.png)|![](md/rgb/rot.png)

#### Image Shear

`../bin/imagepro pics/eleph.png gs/shear_x.png -shear x 0.8 1`

Grayscale   | Horizontal Shear
:----------:|:---------------:
![](md/gs/original.png)|![](md/gs/shear_x.png)

`../bin/imagepro pics/eleph.png gs/shear_y.png -shear y -0.2 1`

Grayscale   | Vertical Shear
:----------:|:---------------:
![](md/gs/original.png)|![](md/gs/shear_y.png)

`../bin/imagepro pics/rango.png rgb/shear_x.png -shear x -0.5 2`

RGB Image    | Horizontal Shear
:-----------:|:---------------:
![](md/rgb/original.png)|![](md/rgb/shear_x.png)

`../bin/imagepro pics/rango.png rgb/shear_y.png -shear y 0.4 2`

RGB Image    | Vertical Shear
:-----------:|:--------------:
![](md/rgb/original.png)|![](md/rgb/shear_y.png)

#### Image Scale

`../bin/imagepro pics/eleph.png gs/scale.png -scale 1.5 0.75 1`

Grayscale   | Scaling
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/scale.png)

`../bin/imagepro pics/rango.png rgb/scale.png -scale 0.65 1.2 2`

RGB Image    | Scaling
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/scale.png)


## Visual Filters

#### Grayscale (BT.601)

`../bin/imagepro pics/rango.png rgb/gs.png -gs`

RGB Image    | Grayscale
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/gs.png)

#### Grayscale (Mean)

`../bin/imagepro pics/rango.png rgb/gs_mean.png -gsm`

RGB Image    | Grayscale
:-----------:|:--------------:
![](md/rgb/original.png)|![](md/rgb/gs_mean.png)

#### Adjust Brightness

`../bin/imagepro pics/eleph.png gs/br.png -br 50`

Grayscale   | Brightness
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/br.png)

`../bin/imagepro pics/rango.png rgb/br.png -br 60`

RGB Image    | Brightness
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/br.png)

#### Adjust Contrast

`../bin/imagepro pics/eleph.png gs/cont.png -cont 30`

Grayscale   | Contrast
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/cont.png)

`../bin/imagepro pics/rango.png rgb/cont.png -cont 45`

RGB Image    | Contrast
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/cont.png)

#### Gamma Correction

`../bin/imagepro pics/eleph.png gs/gamma.png -g 3.0`

Grayscale   | Gamma
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/gamma.png)

`../bin/imagepro pics/rango.png rgb/gamma.png -g 3.0`

RGB Image    | Gamma
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/gamma.png)

#### Equalization

`../bin/imagepro pics/eleph-uneq.png gs/eq.png -eq`

Unequalized   | Equalized
:------------:|:-----------:
![](md/gs/eq-in.png)  |![](md/gs/eq.png)
![](md/histogram/gs_uneq.png)|![](md/histogram/gs_eq.png)

`../bin/imagepro pics/rango-uneq.png rgb/eq.png -eq`

Unequalized    | Equalized
:-------------:|:------------:
![](md/rgb/eq-in.png)  |![](md/rgb/eq.png)
![](md/histogram/rgb_uneq.png)|![](md/histogram/rgb_eq.png)

#### Color Inversion

`../bin/imagepro pics/eleph.png gs/inv.png -invert`

Grayscale   | Inversion
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/inv.png)

`../bin/imagepro pics/rango.png rgb/inv.png -invert`

RGB Image    | Inversion
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/inv.png)

#### Mean Blur

`../bin/imagepro pics/eleph.png gs/mean.png -mean`

Grayscale   | Mean Blur
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/mean.png)

`../bin/imagepro pics/rango.png rgb/mean.png -mean`

RGB Image    | Mean Blur
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/mean.png)

#### Gaussian Blur

`../bin/imagepro pics/eleph.png gs/gauss.png -gauss`

Grayscale   | Gaussian Blur
:----------:|:------------:
![](md/gs/original.png)|![](md/gs/gauss.png)

`../bin/imagepro pics/rango.png rgb/gauss.png -gauss`

RGB Image    | Gaussian Blur
:-----------:|:-------------:
![](md/rgb/original.png)|![](md/rgb/gauss.png)

#### Noise Removal (Median)

`../bin/imagepro pics/eleph-noise.png gs/noise.png -noise`

Noise       | Noise Removal
:----------:|:-----------:
![](md/gs/noise-in.png)|![](md/gs/noise.png)

`../bin/imagepro pics/rango-noise.png rgb/noise.png -noise`

Noise        | Noise Removal
:-----------:|:------------:
![](md/rgb/noise-in.png)|![](md/rgb/noise.png)

#### Sharpen Image (Laplace)

`../bin/imagepro pics/eleph.png gs/laplace.png -laplace`

Grayscale   | Laplace
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/laplace.png)

`../bin/imagepro pics/rango.png rgb/laplace.png -laplace`

RGB Image    | Laplace
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/laplace.png)

#### Emboss

`../bin/imagepro pics/eleph.png gs/emboss.png -emboss`

Grayscale   | Emboss
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/emboss.png)

`../bin/imagepro pics/rango.png rgb/emboss.png -emboss`

RGB Image    | Emboss
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/emboss.png)

#### Glow

`../bin/imagepro pics/eleph.png gs/glow.png -glow`

Grayscale   | Glow
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/glow.png)

`../bin/imagepro pics/rango.png rgb/glow.png -glow`

RGB Image    | Glow
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/glow.png)

#### Add RGB

`../bin/imagepro pics/eleph.png gs/add_rgb.png -rgb+ -10 0 10`

Grayscale   | Add RGB
:----------:|:-------------:
![](md/gs/original.png)|![](md/gs/add_rgb.png)

`../bin/imagepro pics/rango.png rgb/add_rgb.png -rgb+ -40 0 40`

RGB Image    | Add RGB
:-----------:|:--------------:
![](md/rgb/original.png)|![](md/rgb/add_rgb.png)

#### Multiply RGB

`../bin/imagepro pics/eleph.png gs/mul_rgb.png -rgb* 0.8 1.0 1.2`

Grayscale   | Multiply RGB
:----------:|:-------------:
![](md/gs/original.png)|![](md/gs/mul_rgb.png)

`../bin/imagepro pics/rango.png rgb/mul_rgb.png -rgb* 0.6 1.0 1.4`

RGB Image    | Multiply RGB
:-----------:|:--------------:
![](md/rgb/original.png)|![](md/rgb/mul_rgb.png)

#### Bluetone

`../bin/imagepro pics/eleph.png gs/blue.png -blue`

Grayscale   | Bluetone
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/blue.png)

`../bin/imagepro pics/rango.png rgb/blue.png -blue`

RGB Image    | Bluetone
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/blue.png)

#### Floyd Steinberg Dithering

`../bin/imagepro pics/eleph.png gs/floyd.png -floyd`

Grayscale   | Dithering
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/floyd.png)

`../bin/imagepro pics/rango.png rgb/floyd.png -floyd`

RGB Image    | Dithering
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/floyd.png)

#### Pixelize

`../bin/imagepro pics/eleph.png gs/pix.png -pix 10 15`

Grayscale   | Pixelize
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/pix.png)

`../bin/imagepro pics/rango.png rgb/pix.png -pix 15 25`

RGB Image    | Pixelize
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/pix.png)

#### Pixel Spreading

`../bin/imagepro pics/eleph.png gs/spread.png -spr 10 10 3919`

Grayscale   | Spread
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/spread.png)

`../bin/imagepro pics/rango.png rgb/spread.png -spr 15 15 3919`

RGB Image    | Spread
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/spread.png)


## Chained Filter Application

`../bin/imagepro pics/eleph.png gs/chain.png -g 0.5 -rgb* 1.5 1.5 1.5 -gs`

Grayscale   | Chained
:----------:|:-----------:
![](md/gs/original.png)|![](md/gs/chain.png)

`../bin/imagepro pics/rango.png rgb/chain.png -cont -30 -glow -floyd -gauss`

RGB Image    | Chained
:-----------:|:------------:
![](md/rgb/original.png)|![](md/rgb/chain.png)


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
