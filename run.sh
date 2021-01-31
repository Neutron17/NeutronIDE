#!/bin/sh

inp="$*"

touch test.c
echo $inp >> test.c
gcc test.c
rm test.py
read