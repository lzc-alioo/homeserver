#!/usr/bin/env bash
# /home/pi/work/cpolar/cpolarstart.sh add to /etc/rc.local

PATH_BASE=$(cd $(dirname $0); pwd)
RUN_APP=cpolar
LOG_FILE=logs/`date +%Y%m%d%H%M%S`.log


echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_APP start... you can see log:$LOG_FILE"

cd $PATH_BASE
sudo chown -R pi:pi $PATH_BASE/*

cmd="su - pi -c \"$PATH_BASE/cpolar http 8081 \""
nohup $cmd >> $PATH_BASE/$LOG_FILE -log=stdout 2>&1   &
