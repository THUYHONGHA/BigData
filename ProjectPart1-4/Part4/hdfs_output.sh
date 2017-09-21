#!/bin/sh
rm -f output.txt
hadoop fs -get output7/part-r-00000 output.txt
