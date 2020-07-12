
echo "开始执行start.sh..."

path_base=`dirname $0`

#java -jar $path_base/homeserver-0.0.1-SNAPSHOT.jar &
cmd="java -jar $path_base/homeserver-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod"
nohup $cmd >> $path_base/output.log 2>&1  &

# /home/pi/work/build/start.sh add to /etc/rc.local
