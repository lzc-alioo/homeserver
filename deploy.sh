

ssh pi@raspberrypi "mkdir -p /home/pi/work/build/"

#提前配置ssh-key
#scp -c aes128-ctr  build/* pi@raspberrypi:/home/pi/work/build/
scp -c chacha20-poly1305@openssh.com  -o "Compression yes" build/* pi@raspberrypi:/home/pi/work/build/

#ssh pi@raspberrypi "echo 'restart homeserver start,check homeserver process...'; jps -ml|grep homeserver; /home/pi/work/build/stop.sh;/home/pi/work/build/start.sh;echo 'restart homeserver end,check homeserver process...';jps -ml|grep homeserver"

ssh pi@raspberrypi "chown pi:pi /home/pi/work/build/"
ssh pi@raspberrypi "chmod +x /home/pi/work/build/*.sh"
ssh pi@raspberrypi "mv /home/pi/work/build/cpolar*.sh /home/pi/work/cpolar/"

ssh pi@raspberrypi "/home/pi/work/build/restart.sh"