#!/bin/sh
#
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
pg_dump  -c > $dumpfile                    \
 && echo "dumped database to ${dumpfile}"  \
 || dumping database failed
