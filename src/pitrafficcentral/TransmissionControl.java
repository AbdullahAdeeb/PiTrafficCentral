package pitrafficcentral;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TransmissionControl 
{
        //declare global variables
        InetAddress IPAddressH;
	InetAddress IPAddressV;
	byte[] sendData = new byte[2];
	DatagramSocket socket;
	DatagramPacket sendPacketH;
	DatagramPacket sendPacketV;
	public TransmissionControl()
	{
		
		try 
		{
                        //set IP addresses
			IPAddressH = InetAddress.getByName("10.0.0.42");
			IPAddressV = InetAddress.getByName("10.0.0.44");
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
                //initialize datagram packets
		sendPacketH = new DatagramPacket(sendData, sendData.length,IPAddressH,5005);
		sendPacketV = new DatagramPacket(sendData, sendData.length,IPAddressV,5005);
		
		try 
		{   
                        //initialize the sending socket on a random port
			socket = new DatagramSocket();
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
	}
	public synchronized void sendH(byte[] data)
	{
                //send to the east-west lights
                //set datagram packet to send data
		sendPacketH = new DatagramPacket(data, data.length,IPAddressH,5005);
		try
		{
			socket.send(sendPacketH);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Sent PacketH:\n\tIP: "+sendPacketH.getAddress()+"\n\tData: "+new String(sendPacketH.getData()));
	}
	public synchronized void sendV(byte[] data)
	{
                //send to the north-south lights
                //set datagram packet to send data
		sendPacketV = new DatagramPacket(data, data.length,IPAddressV,5005);
		try
		{
			socket.send(sendPacketV);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		System.out.println("Sent PacketV:\n\tIP: "+sendPacketV.getAddress()+"\n\tData: "+new String(sendPacketV.getData()));
	}
	
}
