package com.hanains.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String HOST_ADDRESS = "127.0.0.1";
	// 루프 백이라고 해서 127.0.0.1은 자기자신의 아이피로 돌아온다.
	private static final int PORT = 9090;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String args[]){
		DatagramSocket datagramSocket = null;
		Scanner scan = new Scanner(System.in);
		try{
			//1.UDP 소켓생성
			datagramSocket = new DatagramSocket();
						
			//2.전송 패킷 생성
			
			while(true){
				System.out.print(">>");
				String data = scan.nextLine();
				byte[] sendData = data.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(
								sendData,
								sendData.length, 
								new InetSocketAddress(HOST_ADDRESS,PORT));
				
				//3.데이터 전송
				datagramSocket.send(sendPacket);

				//4.데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
				
				//5.데이터 출력
				data = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
				System.out.println(data);

			}
						
		}catch(Exception e){
			log("error : " + e);
		}finally{
			datagramSocket.close();
		}
	}
	
	public static void log(String message){
		System.out.println("[UDP Echo Client]" + message);
	}
}
