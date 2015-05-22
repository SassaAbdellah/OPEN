#!/bin/sh
#
#
#  do a "supersecure" dump of the openride database 
#  "supersecure" here means that --column-insert option is used.
#
#
#
# source configuration
#
. ./postgresql.cfg
#
#
timestamp=$(date "+%Y%m%d%H%M-%s")
dumpfile="/tmp/openride-dump.${timestamp}.sql"
#
#
#
#
pg_dump --column-inserts  -c > $dumpfile   \
 && echo "dumped database to ${dumpfile}"  \
 || dumping database failed
