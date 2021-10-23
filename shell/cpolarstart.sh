#!/usr/bin/env bash
# /home/pi/work/cpolar/start.sh add to /etc/rc.local

PATH_BASE=`dirname $0`
RUN_APP=cpolar
LOG_FILE=logs/`date +%Y%m%d%H%M%S`.log


echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_APP start... you can see log:$LOG_FILE"

cd $PATH_BASE
sudo chown -R pi:pi $PATH_BASE/*

cmd="$PATH_BASE/cpolar http 8081"
nohup $cmd >> $PATH_BASE/$LOG_FILE -log=stdout 2>&1   &
