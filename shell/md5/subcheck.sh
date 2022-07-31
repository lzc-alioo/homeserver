sublength=12

if [ "" != "$1" ]
then
    sublength=$1
fi

# more md5.txt |awk -F ',' '{print $1}'|sort|cut  -c  1-$sublength|sort|uniq -c |sort

more md5.txt |cut  -c  1-$sublength|sort|uniq -c |sort | grep -av "1 " | awk '{if($0!="")print}'

param= $(more md5.txt |cut  -c  1-$sublength|sort|uniq -c |sort | grep -av "1 "|awk -F ' ' '{print $2}' )
echo $param

