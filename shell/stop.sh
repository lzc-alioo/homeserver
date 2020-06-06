
echo "开始执行stop.sh..."

pid=$(ps aux|grep homeserver|grep -v grep |awk -F ' ' '{print $2}')

if [ "" != "$pid" ]; then
    kill -9 $pid
    echo "执行stop.sh时成功kill掉进程pid:$pid"
else
    echo "执行stop.sh时发现进程不存在，直接跳过"
fi

