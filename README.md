# homeserver


## iptables 端口转发规则

sudo iptables -t nat -A PREROUTING -d 192.168.16.198 -p tcp --dport 80 -j DNAT --to-destination 192.168.16.1:80
sudo iptables -t nat -A POSTROUTING -d 192.168.16.1 -p tcp --dport 80 -j SNAT --to 192.168.16.198
sudo iptables -t nat  --list

备注：这种方式是全局代理，如果想替换部分网页内容就搞不定



## nginx转发

https://www.raspberrypi.org/documentation/remote-access/web-server/nginx.md

sudo apt install nginx
sudo /etc/init.d/nginx start
sudo lsof -i:80


pi@raspberrypi:~/work $ curl  http://localhost
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>


### nginx牛逼的配置，可以改写反向代理的html或者js内容,下面的案例就展示了改写js的代码

在如下目录下新建文件blink.conf,内容如下
/etc/nginx/conf.d/blink.conf

upstream www.iptest.cn {
    server 192.168.16.1:80;
}

server {
    listen 8080 default_server;
    listen [::]:8080 default_server;


    root /home/pi/work/nginx/html;
    # Add index.php to the list if you are using PHP
    index index.html index.htm index.nginx-debian.html;
    server_name _;
    
    location  /routeindex.html {
        root   /home/pi/work/nginx/html;
        index  routeindex.html;
    }
    
    location / {
        proxy_pass http://www.iptest.cn$request_uri;
    
        #反向代理规则#
        #设置反向代理头部，有时候源站响应的是gzip格式，替换的时候会有问题，可通过此项解决#
        proxy_set_header Accept-Encoding deflate;
        #proxy_set_header Accept-Encoding "";
    
        #js/jquery.cookie.js
        subs_filter '<script type="text/javascript" src="http://hiwifi.wiair.com/router/swifi_switch.js"></script>' '<script type="text/javascript">console.log("update by alioo 20200701")</script>';
        
        #common.js
        subs_filter 'document.domain' 'document.domain+":8080"';
        subs_filter 'http://static.wiair.com/conf/model.js' '';
    
        #替换城市、运营商信息，nginx此替换模块支持中文替换#
        #subs_filter '福建省龙岩市 移动' '河北省唐山市 电信';
        
        #指定被替换的MIME类型#
        subs_filter_types text/html text/javascript ;
    
        #指定字符串替换次数，on表示只替换第一次匹配到的字符，off表示替换所有匹配到的字符#
        sub_filter_once off;
    }
}


sudo /etc/init.d/nginx reload
tail -f /var/log/nginx/*



