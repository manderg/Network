package com.java.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	private static final int PORT = 5050;
	
	public static void main(String args[]){
		ServerSocket serverSocket = null;
		try{
			//1.소켓 생성
			serverSocket = new ServerSocket();
			String localhost = InetAddress.getLocalHost().getHostAddress();
			
			//2.바인딩
			serverSocket.bind(new InetSocketAddress(localhost,PORT));
			System.out.println("[서버]바인딩" + localhost + ":" + PORT);
			while(true){
				//3.accept
				Socket socket = serverSocket.accept();
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}			
		}catch(IOException e){
			e.printStackTrace();
		}	
	}
}
