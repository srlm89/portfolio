#!/bin/bash


PIC=$1
OUT=$2

if [ $# != 2 ]; then
    echo "Usage:    $ histogram.sh <input-pic> <output-pic>"
    exit 1
fi

if [ ! -e "$PIC" ]; then
    echo "Picture does not exist: $1"
    exit 1
fi

if [ -e "$OUT" ]; then
    echo "Histogram already exists: $2"
    exit 1
fi

convert $1 -define histogram_unique-colors=true -format %c histogram:$2

