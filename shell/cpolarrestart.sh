

RUN_APP=cpolar

echo ""
echo ""
echo ""
echo ""
echo ""
echo "$(date "+%Y-%m-%d %H:%M:%S") === app:$RUN_APP event:restart === start,check cpolar process...";
ps aux|grep $RUN_APP

/home/pi/work/cpolar/cpolarstop.sh;
/home/pi/work/cpolar/cpolarstart.sh;

echo "$(date "+%Y-%m-%d %H:%M:%S") === app:$RUN_APP event:restart === end,check cpolar process...";
ps aux|grep $RUN_APP



