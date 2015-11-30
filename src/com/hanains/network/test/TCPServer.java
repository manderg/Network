package com.hanains.network.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static final int PORT = 5050;
	public static void main(String args[]){
		ServerSocket serverSocket = null;
		try{
			//1. 소켓서버생성
			serverSocket = new ServerSocket();
			String localhost = InetAddress.getLocalHost().getHostAddress();
			
			//2. 바인딩
			serverSocket.bind(new InetSocketAddress(localhost,PORT));
			System.out.println("[서버]바인딩" + localhost + ":" + PORT);
			
			//3. 연결 요청 대기(accept)
			Socket socket = serverSocket.accept();
			
			//4. 연결 성공
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
				//InetSocketAddress는 SocketAddress의 자식이다.
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버]연결됨 from " + remoteHostAddress + ":" + remoteHostPort);
			
			
			//7. 소켓 닫기
			if(socket.isClosed()==false){
				socket.close();
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(serverSocket != null && serverSocket.isClosed()==false){
					serverSocket.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
}
