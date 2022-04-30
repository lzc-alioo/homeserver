more md5.txt |awk -F ',' '{print $1}'|sort|uniq -c |sort
