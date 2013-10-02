package pitrafficcentral;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TransmissionControl 
{
	InetAddress IPAddressH;
	InetAddress IPAddressV;
	byte[] sendData = new byte[2];
	DatagramSocket socket;
	DatagramPacket sendPacketH;// = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	DatagramPacket sendPacketV;
	public TransmissionControl()
	{
		
		try 
		{
			IPAddressH = InetAddress.getByName("10.0.0.42");
			IPAddressV = InetAddress.getByName("10.0.0.44");
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		sendPacketH = new DatagramPacket(sendData, sendData.length,IPAddressH,5005);
		sendPacketV = new DatagramPacket(sendData, sendData.length,IPAddressV,5005);
		
		try 
		{
			socket = new DatagramSocket();
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
	}
	public synchronized void sendH(byte[] data)
	{
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
