#!/bin/sh

hadoop fs -rmr output5
hadoop jar Stripes.jar Stripes input5/input.txt output5

echo "DONE!"

