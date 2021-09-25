
# /home/pi/work/build/start.sh add to /etc/rc.local

sleep 5

PATH_BASE=`dirname $0`
RUN_JAR=homeserver-0.0.1-SNAPSHOT.jar
FILE_OUTPUT=logs/`date +%Y%m%d%H%M%S`.log

echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_JAR start... you can see log:$LOG_FILE"

cd $PATH_BASE
#java -jar $PATH_BASE/homeserver-0.0.1-SNAPSHOT.jar &
cmd="java -jar $PATH_BASE/homeserver-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod"
nohup $cmd >> $PATH_BASE/$FILE_OUTPUT 2>&1  &

# 查询日志检测java程序是否启动成功
echo "$(date "+%Y-%m-%d %H:%M:%S") checking if started ..."
while [ -f $PATH_BASE/$FILE_OUTPUT ]
do
    current=`date +%Y-%m-%d\ %H:%M`
    result=`grep "$current" $PATH_BASE/$FILE_OUTPUT | grep "Started HomeserverApplication"`
    if [ "x$result" != "x" ]
    then
        echo "$(date "+%Y-%m-%d %H:%M:%S") springboot start ..."
        break
    else
        echo "$(date "+%Y-%m-%d %H:%M:%S") waiting for start..."
        sleep 2s
    fi
done

echo "$(date "+%Y-%m-%d %H:%M:%S") $RUN_JAR started success."
