package pitrafficcentral;

import java.io.*; 
import java.net.*;

public class UdpControl extends Thread
{
	DatagramSocket socket;
	DatagramPacket receiveP;
	
	public UdpControl()
	{	
		
		byte[] temp = new byte[1];
		temp[0] = 1;
		receiveP = new DatagramPacket(temp, temp.length);
		
		try 
		{
			socket = new DatagramSocket(5005);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
		this.start();
	}
	public void receive()
	{
		
		while(true)
		{
			try 
			{
				socket.receive(receiveP);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			System.out.println("Received Packet:\n\tfrom IP: "+receiveP.getAddress()+"\n\tData: "+new String(receiveP.getData()));
			String temp = new String(receiveP.getData());
			PiTrafficCentral.rcvData(temp);
		}
	}
	public void run()
	{	
		receive();
	}
	
}
