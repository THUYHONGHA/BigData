#!/bin/sh

hadoop fs -rmr output7
hadoop jar Stripes.jar Stripes input7/input.txt output7

echo "DONE!"

