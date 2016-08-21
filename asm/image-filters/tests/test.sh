#!/bin/bash
# ./test.sh 2>&1 | tee results.txt
# IDENTIFY=identify -format "dim:(%wx%h)-(hash:%#)"

BINFILE=../bin/imagepro
VALGRIND=valgrind

LOG_TEST=./tests.log
LOG_VALGRIND=./valgrind.log

INPUTDIR=./pics/test-in
OUTPUTDIR=./pics/test-out
EXPECTEDDIR=./pics/test-expected


function run_memory_check() {
    CMD="$BINFILE $INPUT $@"
    echo $CMD | tee --append $LOG_TEST
    $VALGRIND --log-file=$LOG_VALGRIND --error-exitcode=1 -q $CMD 2>&1 1>>$LOG_TEST
    if [ $? != "0" ]; then
        echo "ERROR: memory check failed"
        exit 1
    fi
}

function run_comparison() {
    NAME=`basename $1`
    HASH_EXPECTED=`grep -hr "$NAME" "$EXPECTEDDIR" | cut -f2`
    HASH_ACTUAL=`identify -format "%w-%h-%#" $1`
    if [ "$HASH_EXPECTED" != "$HASH_ACTUAL" ]; then
        echo "Pictures do not match:"
        echo "    expected: $HASH_EXPECTED"
        echo "      actual: $HASH_ACTUAL"
        exit 1
    fi
}


function run() {
    TIME_BEG=`date +%s.%N`
    run_memory_check $@
    TIME_END=`date +%s.%N`
    TIME_DELTA=`echo "$TIME_END - $TIME_BEG" | bc -l`
    echo "{ $TIME_DELTA seconds }"
    run_comparison $@
}


function test_grayscale() {
    run "$FTAG.grayscale.$FEXT" "-gs"
}


function test_grayscale_mean() {
    run "$FTAG.grayscale_mean.$FEXT" "-gsm"
}


function test_inversion() {
    run "$FTAG.inversion.$FEXT" "-invert"
}


function test_equalization() {
    run "$FTAG.equalization.$FEXT" "-eq"
}


function test_floydsteinberg() {
    run "$FTAG.floyd.$FEXT" "-floyd"
}


function test_mean_blur() {
    run "$FTAG.meanblur.$FEXT" "-mean"
}


function test_gaussianblur() {
    run "$FTAG.gaussianblur.$FEXT" "-gauss"
}


function test_median_noise() {
    run "$FTAG.mediannoise.$FEXT" "-noise"
}


function test_laplace_sharpen() {
    run "$FTAG.laplace.$FEXT" "-laplace"
}


function test_emboss() {
    run "$FTAG.emboss.$FEXT" "-emboss"
}


function test_bluetone() {
    run "$FTAG.bluetone.$FEXT" "-blue"
}


function test_glow() {
    run "$FTAG.glow.$FEXT" "-glow"
}


function test_add_rgb() {
	VAL=-126
	while [ $VAL -le 126 ]; do
        run "$FTAG.add_rgb.$VAL.0.0.$FEXT" "-rgb+" $VAL 0 0
        run "$FTAG.add_rgb.0.$VAL.0.$FEXT" "-rgb+" 0 $VAL 0
        run "$FTAG.add_rgb.0.0.$VAL.$FEXT" "-rgb+" 0 0 $VAL
		VAL=$(( $VAL + 84 ))
	done
}

function test_mul_rgb() {
	X=5
    precision=2
	while [ $X -le 15 ]; do
        VAL=$(echo "scale=$precision; $X/10" | bc)
        run "$FTAG.mul_rgb.$X.0.0.$FEXT" "-rgb*" $VAL 1.0 1.0
        run "$FTAG.mul_rgb.0.$X.0.$FEXT" "-rgb*" 1.0 $VAL 1.0
        run "$FTAG.mul_rgb.0.0.$X.$FEXT" "-rgb*" 1.0 1.0 $VAL
		X=$(( $X + 10 ))
	done
}


function test_brightness() {
	BRI=-128
	while [ $BRI -le 128 ]; do
        run "$FTAG.brightness.$BRI.$FEXT" "-br" $BRI
		BRI=$(( $BRI + 64 ))
	done
}


function test_contrast() {
	CON=-128
	while [ $CON -le 128 ]; do
        run "$FTAG.contrast.$CON.$FEXT" "-cont" $CON
		CON=$(( $CON + 64 ))
	done
}


function test_gamma() {
	GMM=2
    precision=2
	while [ $GMM -le 50 ]; do
		gamma=$(echo "scale=$precision; $GMM/10" | bc)
        run "$FTAG.gamma.$GMM.$FEXT" "-g" $gamma
		GMM=$(( $GMM + 16 ))
	done
}


function test_translation() {
	AXIS_X=-50
	while [ $AXIS_X -le 50 ]; do
		AXIS_Y=-50
		while [ $AXIS_Y -le 50 ]; do
            run "$FTAG.translation.$AXIS_X.$AXIS_Y.$FEXT" "-t" $AXIS_X $AXIS_Y
			AXIS_Y=$(( $AXIS_Y + 50 ))
		done
		AXIS_X=$(( $AXIS_X + 50 ))
	done
}


function test_rotation() {
	ANGLE=0
	while [ $ANGLE -le 360 ]; do
        run "$FTAG.rotation.1.$ANGLE.$FEXT" "-r" $ANGLE 1 # neighbour interpolation
        run "$FTAG.rotation.2.$ANGLE.$FEXT" "-r" $ANGLE 2 # bilinear interpolation
		ANGLE=$(( $ANGLE + 72 ))
	done
}


function test_shear_horizontal() {
	ANGLE=-10
    precision=2
	while [ $ANGLE -le 10 ]; do
	    x=$(echo "scale=$precision; $ANGLE/10" | bc)
        run "$FTAG.shear.1.x.$ANGLE.$FEXT" "-shear x" $x 1 # neighbour interpolation
        run "$FTAG.shear.2.x.$ANGLE.$FEXT" "-shear x" $x 2 # bilinear interpolation
		ANGLE=$(( $ANGLE + 10 ))
	done
}


function test_shear_vertical() {
	ANGLE=-10
    precision=2
	while [ $ANGLE -le 10 ]; do
	    y=$(echo "scale=$precision; $ANGLE/10" | bc)
        run "$FTAG.shear.1.y.$ANGLE.$FEXT" "-shear y" $y 1 # neighbour interpolation
        run "$FTAG.shear.2.y.$ANGLE.$FEXT" "-shear y" $y 2 # bilinear interpolation
		ANGLE=$(( $ANGLE + 10 ))
	done
}


function test_scaling() {
	HOR=6
    precision=2
	while [ $HOR -le 50 ]; do
		VER=6
		while [ $VER -le 50 ]; do
			x=$(echo "scale=$precision; $HOR/10" | bc)
		    y=$(echo "scale=$precision; $VER/10" | bc)
            run "$FTAG.scale.1.$HOR.$VER.$FEXT" "-scale" $x $y 1 # neighbour interpolation
            run "$FTAG.scale.2.$HOR.$VER.$FEXT" "-scale" $x $y 2 # bilinear interpolation
			VER=$(( $VER + 22 ))
		done
		HOR=$(( $HOR + 22 ))
	done
}


function test_spread() {
    seed=5330
	AXIS_X=1
	while [ $AXIS_X -le 5 ]; do
		AXIS_Y=1
		while [ $AXIS_Y -le 5 ]; do
            run "$FTAG.spread.$AXIS_X.$AXIS_Y.$FEXT" "-spr" $AXIS_X $AXIS_Y $seed
			AXIS_Y=$(( $AXIS_Y + 4 ))
		done
		AXIS_X=$(( $AXIS_X + 4 ))
	done
}


function test_pixelize() {
	AXIS_X=2
	while [ $AXIS_X -le 50 ]; do
		AXIS_Y=2
		while [ $AXIS_Y -le 50 ]; do
            run "$FTAG.pixelize.$AXIS_X.$AXIS_Y.$FEXT" "-pix" $AXIS_X $AXIS_Y
			AXIS_Y=$(( $AXIS_Y + 24 ))
		done
		AXIS_X=$(( $AXIS_X + 24 ))
	done
}


test_suite=(
    #test_brightness
    #test_contrast
    #test_gamma
    #test_inversion
    #test_equalization
    #test_median_noise
    #test_emboss
    #test_laplace_sharpen
    #test_mean_blur
    #test_glow
    #test_gaussianblur
    #test_translation
    #test_spread
    #test_pixelize
    #test_rotation
    #test_scaling
    #test_shear_horizontal
    #test_shear_vertical
    #test_grayscale
    #test_grayscale_mean
    #test_add_rgb
    #test_mul_rgb
    #test_bluetone
    #test_floydsteinberg
)


if [ -f $LOG_VALGRIND ]; then
    echo "Deleting log file: $LOG_VALGRIND"
    rm $LOG_VALGRIND
fi

if [ -f $LOG_TEST ]; then
    echo "Deleting log file: $LOG_TEST"
    rm $LOG_TEST
fi

if [ -d $OUTPUTDIR ]; then
    echo "Cleaning output directory: $OUTPUTDIR"
    rm -rf $OUTPUTDIR
fi

mkdir $OUTPUTDIR
echo 'Starting tests...'
for test in ${test_suite[*]}; do
    echo "[Executing: $test]"
    for FILE in $( ls $INPUTDIR ); do
        FTAG=$OUTPUTDIR/`echo $FILE | cut -d. -f1,2`
        FEXT=`echo $FILE | cut -d. -f4`
        INPUT=$INPUTDIR/$FILE
        $test
    done
done
echo "Tests ended successfully"

