#!/bin/bash

PICDIR=$1

if [ -z "$PICDIR" ]; then
    echo "Usage:    $ hashgen.sh <picture-dir>"
    exit 1
fi

ls -1 "$PICDIR" | xargs -I _ bash -c "paste <(echo _) <(identify -format '%w-%h-%#' $PICDIR/_)"
