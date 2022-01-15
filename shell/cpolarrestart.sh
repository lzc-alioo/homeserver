

RUN_APP=cpolar

echo ""
echo ""
echo ""
echo ""
echo ""
echo "$(date "+%Y-%m-%d %H:%M:%S") restart $RUN_APP start,check cpolar process...";
ps aux|grep $RUN_APP

/home/pi/work/cpolar/cpolarstop.sh;
/home/pi/work/cpolar/cpolarstart.sh;

echo '$(date "+%Y-%m-%d %H:%M:%S") restart $$RUN_APP end,check cpolar process...';
ps aux|grep $RUN_APP



