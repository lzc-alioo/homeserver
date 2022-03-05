#!/usr/bin/python
#-- coding:utf-8 --
#摇控器实验 阻塞模式，等待摇控器信号输入
#摇控器实验 加入声音：提示摇控器输入已接收
#摇控器实验 声音GPIO.setmode(GPIO.BOARD)改成GPIO.setmode(GPIO.BCM)



import pylirc, time
import RPi.GPIO as GPIO

blocking = 1
Buzzer = 17   #蜂鸣器。GPIO.BOARD 11 /GPIO.BCM 17

def setup():
    GPIO.setwarnings(False)
    # GPIO.setmode(GPIO.BOARD)        # Numbers GPIOs by physical location  GPIO.BOARD 11
    GPIO.setmode(GPIO.BCM)        # Numbers GPIOs by physical location  GPIO.BCM 17
    GPIO.setup(Buzzer, GPIO.OUT)    # Set pins' mode is output
    global Buzz                     # Assign a global variable to replace GPIO.PWM
    Buzz = GPIO.PWM(Buzzer, 440)    # 440 is initial frequency.
     


def getCommand(code):
    return code[0]["config"] 
        

def soundStart(soundValue):
    Buzz.start(50) 
    Buzz.ChangeFrequency(soundValue)
    time.sleep(0.3)


def soundStop():
    Buzz.stop()                 # Stop the buzzer


def loop():
    print("阻塞模式，等待摇控器信号输入")
    while True:
        code = pylirc.nextcode(1)
        command=getCommand(code)
        print 'code: ' , code, "////command:" , command
        
        soundStart(131)
        soundStop()

        executeCmd(command)


def executeCmd(command):
    if command == 'KEY_CHANNEL':
        print 't_up'
        # t_up(20,0.5)
    if command == 'KEY_NEXT':
        print 't_stop'
        # t_stop(0.5)
    if command == 'KEY_PREVIOUS':
        print 't_left'
        # t_left(20,0)
    if command == 'KEY_PLAYPAUSE':
        print 't_right'
        # t_right(20,0)
    if command == 'KEY_VOLUMEUP':
        print 't_down'
        # t_down(20,0)
        

if __name__ == '__main__':
    setup()

    # pylirc.init("pylirc", "/home/pi/ZYRobot/ircontrol/conf", blocking)
    pylirc.init("pylirc", "./conf", blocking)
    
    try:
        loop()
    except KeyboardInterrupt:
        destroy()




