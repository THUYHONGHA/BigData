#!/bin/sh

hadoop fs -rmr input7
hadoop fs -mkdir input7 2>&1
hadoop fs -put ./input.txt input7/ 
