#!/bin/bash
# Creates pictures of different size for each image in $BASEDIR

BASEIMGDIR=./pics/base
TESTINDIR=./pics/test-in

sizes=(200x200 201x201 202x202 203x203 204x204 205x205 206x206
       207x207 208x208 256x256 512x512 513x513 1777x1991 3111x3917)

mkdir $TESTINDIR
for f in $( ls $BASEIMGDIR );
do
    echo $f
    PREFIX=`echo $f | cut -d. -f1`
    EXT=`echo $f | cut -d. -f2`
    REAL=`identify -format '%wx%h' "$BASEIMGDIR/$f"`
    for s in $REAL ${sizes[*]}
    do
        echo $s
        convert -resize $s! "$BASEIMGDIR/$f" "$TESTINDIR/$PREFIX.$s.in.$EXT"
    done
done
