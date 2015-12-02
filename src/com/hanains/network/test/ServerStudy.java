package com.hanains.network.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStudy {
	private static final int PORT = 5050;
	
	public static void main(String args[]){
		//1.소켓생성
		ServerSocket serverSocket = null;
		
		try{
			serverSocket = new ServerSocket();
			String localhost = InetAddress.getLocalHost().getHostAddress();
			
			//2.소켓 바인딩
			serverSocket.bind(new InetSocketAddress(localhost,PORT));
			
			//3.accept
			Socket socket = serverSocket.accept();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
