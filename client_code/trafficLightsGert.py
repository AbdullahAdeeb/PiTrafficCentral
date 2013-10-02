#import
import socket
from time import sleep
from threading import Thread
import RPi.GPIO as gert
import sys

def command(object):
        #command function, changes the LEDs depending on the argument
        if(object == "1"):
                print "Green"
                gert.output(portsOut[0], 1)
                gert.output(portsOut[1], 0)
                gert.output(portsOut[2], 0)
        elif(object == '2'):
                #orange light
                print "Orange"
                gert.output(portsOut[0], 0)
                gert.output(portsOut[1], 1)
                gert.output(portsOut[2], 0)
        elif(object =="3"):
                #red light      
                print "RED"
                gert.output(portsOut[0], 0)
                gert.output(portsOut[1], 0)
                gert.output(portsOut[2], 1)

def receive():
        #receive UDP packets from java server
        t1 = Thread(target = pedestrianCrossing, args = ())
        t1.start()

        #receive UDP packet from server
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind(("10.0.0.42", UDP_Port))
        while True:
                data, addr = sock.recvfrom(1)
                print"Received Data"
                print"Data: "+data
                command(data)
        return 0


def send(message):
        #send UDP packet to server with the data being passed as message
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.sendto(message, (UDP_Server, UDP_Port))
def pedestrianCrossing():
        #a continuous loop, run as a separate thread, waiting for the button input
        while True:
                if gert.input(25) == 0:
                        send("1")
                        print "North-South"
                        sleep(0.5)
                elif gert.input(24) == 0:
                        send("2")
                        print "East-West"
                        sleep(0.5)

gert.cleanup()
board_type= sys.argv[-1]
#9 = red, 10 = orange, 11 = green
portsOut = [11, 10, 9]
#24 = north-south, 25 = east-west
portsIn = [24, 25]
gert.setmode(gert.BCM)

#initialization of the LEDs and buttons
for x in range(0,3):
        gert.setup(portsOut[x], gert.OUT)
        gert.output(portsOut[x], 0)

for i in range(0, 2):
        gert.setup(portsIn[i], gert.IN, pull_up_down = gert.PUD_UP)

#settings for UDP commands
UDP_Server = "10.0.0.41"
UDP_Port = 5005
receive()


