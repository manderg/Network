package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT = 9393;
	
	public static void main(String args[]){
		
		List<Writer> listWriters = new ArrayList<Writer>();
		ServerSocket serverSocket = null;
		try{
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			String localhost = InetAddress.getLocalHost().getHostAddress();
			
			//2. 바인드
			serverSocket.bind(new InetSocketAddress(localhost,PORT));
			System.out.println("연결기다림" + localhost + ":" + PORT);
			while(true){
				//3. accept
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket,listWriters).start();	
			}			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			
		}
	}

	public static void log(String string) {
		System.out.println(string);
	}
}
