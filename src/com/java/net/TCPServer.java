package com.java.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static final int PORT = 5353;
	
	public static void main(String args[]){
		ServerSocket serverSocket = null;

		try{
			//1. 소켓만들기
			serverSocket = new ServerSocket();
			String localhost = InetAddress.getLocalHost().getHostAddress();
			
			//2. 바인딩하기
			serverSocket.bind(new InetSocketAddress(localhost,PORT));
			System.out.println("[서버]바인딩" + localhost + ":" + PORT);
		
			//3. accept 하기
			Socket socket = serverSocket.accept();
			
			//4. 연결성공
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			
			
		}catch(IOException e){
			System.out.println("[서버]에러:" + e);
		}
		
		
	}
}
