#!/usr/bin/python
#-- coding:utf-8 --
#摇控器实验

import pylirc, time

blocking = 0


def loop():
    print("非阻塞模式，每隔1秒检查信号输入")

    while True:
        s = pylirc.nextcode(1)
        print 'nextcode: ',s
        time.sleep(1)
 

if __name__ == '__main__':

	pylirc.init("pylirc", "./conf", blocking)
	try:
        loop()
    except KeyboardInterrupt:
        destroy()