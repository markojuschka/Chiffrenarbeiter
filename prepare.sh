#!/bin/bash

if [ $# == 1 ]
then

# Löschen der bisherigen Dateien input.txt, output.txt, result.txt und subst.cfg
rm -f input.txt
rm -f output.txt
rm -f result.txt
rm -f subst.cfg

# Parameter demo1, demo2 oder demo3
if [ "$1" = "demo1" ]
then 
	cp ./demo/input_demo1.txt input.txt
fi 
if [ "$1" = "demo2" ]
then 
	cp ./demo/input_demo2.txt input.txt
fi 
if [ "$1" = "demo3" ]
then 
	cp ./demo/input_demo3.txt input.txt
	cp ./demo/subst_demo3.cfg subst.cfg 
fi 
echo "$1 prepared."


else echo "usage: prepare.sh <demo#>"
fi




