#!/bin/bash

set -e

JAR_FILE="$1"

java -cp "$1" xxl.java.Main add Ralph "I shoot birds at the airport"

java -cp "$1" xxl.java.Main show Ralph

java -cp "$1" xxl.java.Main delete Ralph

