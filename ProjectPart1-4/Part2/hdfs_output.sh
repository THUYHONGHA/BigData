#!/bin/sh
rm -f output.txt
hadoop fs -get output5/part-r-00000 output.txt
