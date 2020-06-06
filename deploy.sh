
#提前配置ssh-key
#scp -c aes128-ctr  build/* pi@raspberrypi:/home/pi/work/build/
scp -c chacha20-poly1305@openssh.com  -o "Compression yes" build/* pi@raspberrypi:/home/pi/work/build/


ssh pi@raspberrypi "pwd; echo '你好，alioo,restart homeserver'; /home/pi/work/build/stop.sh; /home/pi/work/build/start.sh"