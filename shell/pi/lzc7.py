#!/usr/bin/python
#-- coding:utf-8 --
#摇控器实验 阻塞模式，等待摇控器信号输入
#摇控器实验 加入声音：提示摇控器输入已接收
#摇控器实验 声音GPIO.setmode(GPIO.BOARD)改成GPIO.setmode(GPIO.BCM)
#摇控器实验 加入运动指令,每次运行3秒结束，等待下一次指令




import pylirc, time
import RPi.GPIO as GPIO

blocking = 1
Buzzer = 17   #蜂鸣器。GPIO.BOARD 11 /GPIO.BCM 17

PWMA   = 18
AIN1   = 22
AIN2   = 27

PWMB   = 23
BIN1   = 25
BIN2   = 24

BtnPin  = 19  #按钮
Gpin    = 5
Rpin    = 6

def setup():
    GPIO.setwarnings(False)
    # GPIO.setmode(GPIO.BOARD)        # Numbers GPIOs by physical location  GPIO.BOARD 11
    GPIO.setmode(GPIO.BCM)        # Numbers GPIOs by physical location  GPIO.BCM 17
    GPIO.setup(Buzzer, GPIO.OUT)    # Set pins' mode is output
    global Buzz                     # Assign a global variable to replace GPIO.PWM
    Buzz = GPIO.PWM(Buzzer, 440)    # 440 is initial frequency.

    # GPIO.setwarnings(False)
    # GPIO.setmode(GPIO.BCM)
    GPIO.setup(AIN2,GPIO.OUT)
    GPIO.setup(AIN1,GPIO.OUT)
    GPIO.setup(PWMA,GPIO.OUT)

    GPIO.setup(BIN1,GPIO.OUT)
    GPIO.setup(BIN2,GPIO.OUT)
    GPIO.setup(PWMB,GPIO.OUT)

    #GPIO.setup(Rpin, GPIO.OUT)     # Set Red Led Pin mode to output
    #GPIO.setup(BtnPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

def getCommand(code):
    return code[0]["config"]



def sound(soundValue):
    soundStart(soundValue)
    soundStop()

def soundStart(soundValue):
    Buzz.start(50)
    Buzz.ChangeFrequency(soundValue)
    time.sleep(0.3)


def soundStop():
    Buzz.stop()                 # Stop the buzzer

def destroy():
	GPIO.cleanup()
	pylirc.exit()

def loop():
    print("阻塞模式，等待摇控器信号输入")
    while True:
        code = pylirc.nextcode(1)
        command=getCommand(code)
        print 'code: ' , code, "////command:" , command

        executeCmd(command)


def executeCmd(command):
    if command == 'KEY_CHANNEL':
        print 't_up'
        sound(131)
        t_up(30,3)
        t_stop(0)
        return
    if command == 'KEY_VOLUMEUP':
        print 't_down'
        sound(196)
        t_down(30,3)
        t_stop(0)
        return
    if command == 'KEY_PREVIOUS':
        print 't_left'
        sound(165)
        t_left(20,3)
        t_stop(0)
        return
    if command == 'KEY_PLAYPAUSE':
        print 't_right'
        sound(175)
        t_right(20,3)
        t_stop(0)
        return
    if command == 'KEY_NEXT':
        print 't_stop'
        sound(147)
        t_stop(0)
        return

    sound(248)


def t_up(speed,t_time):
    L_Motor.ChangeDutyCycle(speed)
    GPIO.output(AIN2,False)#AIN2
    GPIO.output(AIN1,True) #AIN1

    R_Motor.ChangeDutyCycle(speed)
    GPIO.output(BIN2,False)#BIN2
    GPIO.output(BIN1,True) #BIN1
    time.sleep(t_time)

def t_stop(t_time):
    L_Motor.ChangeDutyCycle(0)
    GPIO.output(AIN2,False)#AIN2
    GPIO.output(AIN1,False) #AIN1

    R_Motor.ChangeDutyCycle(0)
    GPIO.output(BIN2,False)#BIN2
    GPIO.output(BIN1,False) #BIN1
    time.sleep(t_time)


def t_down(speed,t_time):
    L_Motor.ChangeDutyCycle(speed)
    GPIO.output(AIN2,True)#AIN2
    GPIO.output(AIN1,False) #AIN1

    R_Motor.ChangeDutyCycle(speed)
    GPIO.output(BIN2,True)#BIN2
    GPIO.output(BIN1,False) #BIN1
    time.sleep(t_time)

def t_left(speed,t_time):
    L_Motor.ChangeDutyCycle(speed)
    GPIO.output(AIN2,True)#AIN2
    GPIO.output(AIN1,False) #AIN1

    R_Motor.ChangeDutyCycle(speed)
    GPIO.output(BIN2,False)#BIN2
    GPIO.output(BIN1,True) #BIN1
    time.sleep(t_time)

def t_right(speed,t_time):
    L_Motor.ChangeDutyCycle(speed)
    GPIO.output(AIN2,False)#AIN2
    GPIO.output(AIN1,True) #AIN1

    R_Motor.ChangeDutyCycle(speed)
    GPIO.output(BIN2,True)#BIN2
    GPIO.output(BIN1,False) #BIN1
    time.sleep(t_time)

if __name__ == '__main__':
    setup()

    L_Motor= GPIO.PWM(PWMA,100)
    L_Motor.start(0)
    R_Motor = GPIO.PWM(PWMB,100)
    R_Motor.start(0)

    pylirc.init("pylirc", "/home/pi/ZYRobot/ircontrol/conf", blocking)
    #pylirc.init("pylirc", "./conf", blocking)
    
    try:
        loop()
    except KeyboardInterrupt:
        destroy()




