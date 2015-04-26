#!/bin/sh
#
#
# Add all the jars in OpenRideServer-ejb/lib to classpath
#
for i in $(find ../../OpenRideServer-ejb/libs/ -name "*.jar" )
do
 export ejbjars="${ejbjars}:${i}"
done
#
java -cp "../../OpenRideServer-ejb/dist/OpenRideServer-ejb.jar:${ejbjars}"  de.fhg.fokus.openride.matching.DriverSearchAlgorithmORS 
