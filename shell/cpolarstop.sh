#!/usr/bin/env bash

RUN_APP=cpolar


echo "开始执行cpolarstop.sh..."

pid=$(ps aux | grep $RUN_APP | grep -v grep | grep -v cpolarstop | awk -F ' ' '{print $2}')

if [ "" != "$pid" ]; then
    sudo kill -9 $pid
    echo "执行cpolarstop.sh时计划kill掉进程pid:$pid,执行结果:$?"
else
    echo "执行cpolarstop.sh时发现进程不存在，直接跳过"
fi





