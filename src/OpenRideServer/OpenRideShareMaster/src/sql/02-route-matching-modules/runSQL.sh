#!/bin/sh
#
# run sql script into openride database
#
cat $1 | psql -d openride -U openride -h localhost
