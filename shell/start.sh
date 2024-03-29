#!/usr/bin/env bash
#/home/pi/work/build/start.sh add to /etc/rc.local like this:
#/home/pi/work/timesync/timesync.sh  2>&1 >> /home/pi/work/timesync/timesync.log &
#/home/pi/work/build/restart.sh
#/home/pi/work/dns/restart.sh
#/home/pi/work/cpolar/cpolarrestart.sh


PATH_BASE=$(cd $(dirname $0); pwd)
RUN_APP=homeserver-0.0.1-SNAPSHOT.jar
LOG_PATH=$PATH_BASE/logs
LOG_FILE=$LOG_PATH/`date +%Y%m%d%H%M%S`.log

mkdir -p $LOG_PATH
echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_APP start... user_dir:$PATH_BASE, and you can see log:$LOG_FILE"

cd $PATH_BASE
sudo chown -R pi:pi $PATH_BASE/*

#java -jar $PATH_BASE/homeserver-0.0.1-SNAPSHOT.jar &
cmd="java -jar $PATH_BASE/$RUN_APP --spring.profiles.active=prod"
#nohup $cmd >> $LOG_FILE 2>&1  &
sudo su - pi  -s /bin/bash -c "nohup $cmd >> $LOG_FILE 2>&1   &"


# 查询日志检测java程序是否启动成功
echo "$(date "+%Y-%m-%d %H:%M:%S") checking if started ..."

while [ -f $LOG_FILE ]
do
    current=`date +%Y-%m-%d\ %H:%M`
    echo "cmd:grep \"$current\" $LOG_FILE | grep \"Started Application\""
    result=`grep "$current" $LOG_FILE | grep "Started Application"`

    if [ "x$result" != "x" ]
    then
        echo "$(date "+%Y-%m-%d %H:%M:%S") springboot start ..."
        break
    else
        echo "$(date "+%Y-%m-%d %H:%M:%S") waiting for start..."
        sleep 2s
    fi
done

echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_APP started success."

rm ${LOG_PATH}/console.log
ln -s ${LOG_FILE} ${LOG_PATH}/console.log