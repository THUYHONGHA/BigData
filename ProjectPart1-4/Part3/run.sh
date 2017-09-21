#!/bin/sh

hadoop fs -rmr output6
hadoop jar Stripes.jar Stripes input6/input.txt output6

echo "DONE!"

