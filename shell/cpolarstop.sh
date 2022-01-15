#!/usr/bin/env bash

#注意事项
#stop脚本中，获取需要停止的进程id，要检查下不要误杀


RUN_APP=cpolar


echo "$(date "+%Y-%m-%d %H:%M:%S") 开始执行cpolarstop.sh..."

pid=$(ps aux | grep $RUN_APP | grep -v grep | grep -v cpolarstop  | grep -v cpolarrestart.sh | awk -F ' ' '{print $2}')

if [ "" != "$pid" ]; then
    sudo kill -9 $pid
    echo "$(date "+%Y-%m-%d %H:%M:%S") 执行cpolarstop.sh时计划kill掉进程pid:$pid,执行结果:$?"
else
    echo "$(date "+%Y-%m-%d %H:%M:%S") 执行cpolarstop.sh时发现进程不存在，直接跳过"
fi





