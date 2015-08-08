#!/bin/sh
#
#
#
#
# create database "openride" and user "openride"
#
for i in $( find sql/createDatabase -name "*.sql" | sort -n )
do
 echo applying $i
 cat $i  | psql 
done
#
#
# once openride database exist, run initialization scripts
# again database "openride"
#
for i in $( find sql/populateDatabase -name "*.sql" | sort -n )
do
 echo applying $i
 cat $i  | psql  -d openride 
done



