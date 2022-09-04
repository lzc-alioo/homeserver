#!/usr/bin/env bash
# /home/pi/work/timesync/timesync.sh add to /etc/rc.local
# /home/pi/work/timesync/timesync.sh  2>&1 >> /home/pi/work/timesync/timesync.log &

sudo ntpdate ntp1.aliyun.com

