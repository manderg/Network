package com.hanains.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class TimeClient {
	private static final int PORT = 9393;
	private static final String HOST_ADDRESS = "127.0.0.1";
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String args[])
	{
		DatagramSocket datagramSocket = null;
		try{
			datagramSocket = new DatagramSocket();
			//보낼 패킷
			String data = "";
			byte[] sendData = data.getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(
							sendData,
							sendData.length, 
							new InetSocketAddress(HOST_ADDRESS,PORT));
			datagramSocket.send(sendPacket);
			
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			datagramSocket.receive(receivePacket);
			
			
			data = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
			System.out.println(data);
			
		}catch(Exception e){
			log("에러 - " +e);
		}
	}
	
	public static void log(String message){
		System.out.println("[UDP클라이언트]: "+ message);
	}
}
