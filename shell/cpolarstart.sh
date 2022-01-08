#!/usr/bin/env bash
# /home/pi/work/cpolar/cpolarstart.sh add to /etc/rc.local

PATH_BASE=$(cd $(dirname $0); pwd)
RUN_APP=cpolar
LOG_FILE=$PATH_BASE/logs/`date +%Y%m%d%H%M%S`.log


echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_APP start... you can see log:$LOG_FILE"

cd $PATH_BASE
#sudo chown -R pi:pi $PATH_BASE/*

cmd="$PATH_BASE/cpolar http 8081 -log=stdout"
nohup $cmd >> $LOG_FILE 2>&1   &
