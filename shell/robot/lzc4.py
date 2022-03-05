#!/usr/bin/python
#-- coding:utf-8 --
#摇控器实验 阻塞模式，等待摇控器信号输入
#摇控器实验 加入声音：提示摇控器输入已接收

import pylirc, time
import RPi.GPIO as GPIO

blocking = 1
Buzzer = 11

def setup():
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BOARD)        # Numbers GPIOs by physical location
    GPIO.setup(Buzzer, GPIO.OUT)    # Set pins' mode is output
    global Buzz                     # Assign a global variable to replace GPIO.PWM
    Buzz = GPIO.PWM(Buzzer, 440)    # 440 is initial frequency.
     

def loop():
    print("阻塞模式，等待摇控器信号输入")
    while True:
        code = pylirc.nextcode(1)
        print 'code: ' , code, "////command:" , getCommand(code)
        
        soundStart(131)
        soundStop()


def getCommand(code):
    return code[0]["config"] 
        

def soundStart(soundValue):
    Buzz.start(50) 
    Buzz.ChangeFrequency(soundValue)
    time.sleep(0.3)


def soundStop():
    Buzz.stop()                 # Stop the buzzer


if __name__ == '__main__':
    setup()

    pylirc.init("pylirc", "./conf", blocking)
    
    try:
        loop()
    except KeyboardInterrupt:
        destroy()




