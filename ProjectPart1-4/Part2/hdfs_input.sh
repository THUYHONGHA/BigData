#!/bin/sh

hadoop fs -rmr input5
hadoop fs -mkdir input5 2>&1
hadoop fs -put ./input.txt input5/ 
