#!/usr/bash
 
NOW=$(date +"%d_%m_%Y")

CURRENT_DIR=~/Desktop/SuperCollider/Live/sketches

P_1=${NOW:0:6}
P_2=${NOW:8:2}
NEW=$P_1$P_2

FILE=sketch_$NEW.scd
cd $CURRENT_DIR

#ls
#echo $FILE

if [ -e $FILE ]
then
    echo "file already exists"
else
    echo -e "/*\nSketch: $NEW\n*/"> $FILE
fi




