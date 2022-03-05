#!/usr/bin/env bash
# /home/pi/ZYRobot/ircontrol/lzc.sh add to /etc/rc.local


echo "开始执行stop.sh..."

procdesc=$(ps aux|grep lzc7|grep -v grep)
echo -e "检测之前是否已启动进程\n$procdesc"

pid=$(echo $procdesc |awk -F ' ' '{print $2}' | xargs)
echo "检测之前是否已启动进程pid:$pid"

if [ "" != "$pid" ]; then
    sudo kill -9 $pid
    echo "执行stop.sh时计划kill掉进程pid:$pid,执行结果:$?"
else
    echo "执行stop.sh时发现进程不存在，直接跳过"
fi

echo "开始执行start.sh..."
PATH_BASE=`dirname $0`
LOG_FILE=logs/`date +%Y%m%d%H%M%S`.log

cmd='python /home/pi/ZYRobot/ircontrol/lzc7.py'

nohup $cmd >> $PATH_BASE/$LOG_FILE 2>&1  &
echo "完成执行start.sh..."
