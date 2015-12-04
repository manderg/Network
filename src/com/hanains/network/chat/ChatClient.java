package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 9393;
	
	public static void main(String args[]){
		
		Socket socket = new Socket();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		//2.서버연결
		try{
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			System.out.println("[클라이언트]서버연결 성공");
			
			//3.IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			//5. ChatClientReceiveThread 시작
			
			
			//4. 닉네임
			System.out.print("닉네임을입력>>");
			String nickname = keyboard.readLine();
			pw.println("join:"+nickname);
			pw.flush();

			while(true){
				String line =br.readLine();
				if("join:ok".equals(line))
				{
					(new ChatClientReceiveThread(socket, br, nickname)).start();
					break;
				}
			}
			
			while(true){
				System.out.print("");
				String input = keyboard.readLine();
				
				if("quit".equals(input) == true){
					pw.println("quit");
					break;
				}else if("to".equals(input) == true){
					pw.println("to:"+input);
				}else{
					pw.println("message:"+input);					
				}	
				pw.flush();
			}
			
		}catch(IOException e){
			System.out.println("클라이언트오류 발생");
		}finally{
			
		}
	}

	private static void close(PrintWriter pw, BufferedReader br, Socket socket) throws IOException {
		br.close();
		pw.close();
		socket.close();
	}
}
