#!/usr/bin/env bash
# /home/pi/work/cpolar/cpolarstart.sh add to /etc/rc.local
# /home/pi/work/cpolar/cpolarrestart.sh 2>&1 >> /home/pi/work/cpolar/crontab-cpolar.log  add to /etc/rc.local



PATH_BASE=$(cd $(dirname $0); pwd)
RUN_APP=cpolar
LOG_FILE=$PATH_BASE/logs/`date +%Y%m%d%H%M%S`.log

sudo su - pi  -s /bin/bash -c "touch $LOG_FILE"

echo "$(date "+%Y-%m-%d %H:%M:%S") === app:$RUN_APP event:start === you can see log:$LOG_FILE"
echo "$(date "+%Y-%m-%d %H:%M:%S") === app:$RUN_APP event:start === you can see log:$LOG_FILE"   >> $LOG_FILE

cd $PATH_BASE
#sudo chown -R pi:pi $PATH_BASE/*

cmd="$PATH_BASE/cpolar http 8081 -log=stdout"
#nohup $cmd >> $LOG_FILE 2>&1  &
sudo su - pi  -s /bin/bash -c "nohup $cmd >> $LOG_FILE 2>&1   &"
