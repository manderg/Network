package com.hanains.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 9393;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String args[]){
		DatagramSocket datagramSocket = null;
		try{
			datagramSocket = new DatagramSocket(PORT);
			log("수신 대기");
			while(true){
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
				
				log("데이터받음");
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = new String(format.format(new Date()));
				byte[] sendData = data.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(
						sendData,
						sendData.length, 
						receivePacket.getAddress(),
						receivePacket.getPort());
				datagramSocket.send(sendPacket);
				log("데이터보냄");
			}
		}catch(Exception e){
			log("에러"+e);
		}finally{
			datagramSocket.close();
		}
	}
	
	public static void log(String message){
		System.out.println("[UDP서버]:" +message);
	}
}
