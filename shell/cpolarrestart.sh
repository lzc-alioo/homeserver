

RUN_APP=cpolar

echo 'restart $RUN_APP start,check cpolar process...';
ps aux|grep $RUN_APP

/home/pi/work/build/cpolarstop.sh;
/home/pi/work/build/cpolarstart.sh;

echo 'restart $$RUN_APP end,check homeserver process...';
ps aux|grep $RUN_APP



