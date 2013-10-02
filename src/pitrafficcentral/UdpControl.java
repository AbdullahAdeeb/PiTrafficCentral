package pitrafficcentral;

import java.io.*; 
import java.net.*;

/**
 *
 * @author lenovo212
 * This class receive the incoming UDP connection on a separate thread
 */
public class UdpControl extends Thread
{
	DatagramSocket socket;
	DatagramPacket receiveP;
	
	/**
     *initialize the thread
     */
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
	/**
     * constant check for incoming connections
     */
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
