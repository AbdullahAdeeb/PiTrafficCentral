#imports
import socket
from time import sleep
from threading import Thread
import piface.pfio as piface

def command(object):
        #command function, depends on input from udp messages
        if(object == "1"):
                #Green Light
                print "Green"
                piface.digital_write(portsOut[0], 1)
                piface.digital_write(portsOut[1], 0)
                piface.digital_write(portsOut[2], 0)
        elif(object == "2"):
                #orange light
                print "Orange"
                piface.digital_write(portsOut[0], 0)
                piface.digital_write(portsOut[1], 1)
                piface.digital_write(portsOut[2], 0)
        elif(object =="3"):
                #red light      
                print "RED"
                piface.digital_write(portsOut[0], 0)
                piface.digital_write(portsOut[1], 0)
                piface.digital_write(portsOut[2], 1)

def receive():
        #receive udp packets from java server
        try:
                #try to create new thread
                t1 = Thread(target = pedestrianCrossing, args = ())
                t1.start()
        except KeyboardInterrupt:
                print "exit button push"

        #receive udp packet from server
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind(("10.0.0.44", UDP_Port))
        while True:
                data, addr = sock.recvfrom(1)
                print"Received Data: "
                print"\tData: "+data
                command(data)


def send(message):
        #send udp packet to server with data = message
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.sendto(message, (UDP_Server, UDP_Port))
def pedestrianCrossing():
        #waits for input from piface
        while True:
                if(piface.digital_read(portsIn[0])):
                        send("1")
                        print "North-South"
                        sleep(0.5)

                elif(piface.digital_read(portsIn[1])):
                        send("2")
                        print "East-West"
                        sleep(0.5)

#init and global variables      
piface.init()
#0 = red, 1 = orange, 2 = green
portsOut = [0,1,2]
#0 = north-south, 1 = east-west
portsIn = [0,1]
#server Ip to send UDP messages to
UDP_Server = "10.0.0.1"
UDP_Port = 5005
receive()



