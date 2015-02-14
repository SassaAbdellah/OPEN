#!/bin/sh
#
# TODO: create 
#
#
#
#
# Include jars from OpenRideServer-ejb
#
CLASSPATH="$CLASSPATH:../../OpenRideServer-ejb/dist/OpenRideServer-ejb.jar" 
for jar in $(ls ../../OpenRideServer-ejb/libs/*.jar)
do
CLASSPATH="${CLASSPATH}:${jar}"
done
#
CLASSPATH="${CLASSPATH}:../dist/OpenRideShareMaster.jar"
#
#
#
# Include jars from joride 
#
CLASSPATH="${CLASSPATH}:../../joride/dist/joride.jar"
for jar in $(ls ../../joride/lib/*.jar)
do
CLASSPATH="${CLASSPATH}:${jar}"
done
#
CLASSPATH="${CLASSPATH}:../dist/joride.jar"
#
#
#
#
# finally include OpenRideShareMaster.jar which include utils
#
CLASSPATH="${CLASSPATH}:../dist/OpenRideShareMaster.jar"
#
#
#
#
java -cp $CLASSPATH de.avci.openrideshare.translationutils.I18nUtil


