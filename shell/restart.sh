

echo 'restart homeserver start,check homeserver process...';
jps -ml|grep homeserver;

/home/pi/work/build/stop.sh;
/home/pi/work/build/start.sh;

echo 'restart homeserver end,check homeserver process...';
jps -ml|grep homeserver;



