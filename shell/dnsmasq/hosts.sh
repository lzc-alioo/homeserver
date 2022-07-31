#!/bin/sh
cd /home/pi/hosts/
echo "***下载 g hosts 文件***"
wget --no-check-certificate https://raw.githubusercontent.com/googlehosts/hosts/ma
ster/hosts-files/hosts -O hosts_g.txt;
echo "***下载 ad hosts 文件***"
wget --no-check-certificate https://raw.githubusercontent.com/vokins/yhosts/master
/hosts -O hosts_ad.txt;
echo "***合并 hosts 文件***"
cat hosts_g.txt hosts_ad.txt > hosts.txt
echo "***复制 hosts 文件***"
sudo cp hosts.txt /etc/hosts
echo "***hosts 文件更新完成"

