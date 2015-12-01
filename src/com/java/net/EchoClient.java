package com.java.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 5050;
	
	public static void main(String args[]){
		Socket socket = null;
		
		try{
			//1.소켓생성
			socket = new Socket();
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			//2.서버연결
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			System.out.println("[클라이언트]서버연결 성공");
			
			//3.IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			
			while((line = keyboard.readLine()) != null){
				if(line.equals("quit")) break;
				pw.println(line);
				pw.flush();
				String echo = br.readLine();
				System.out.println("서버로부터 전달받은 문자열 : " + echo);
			}
			
			pw.close();
			br.close();
			socket.close();
			/*
			//4.쓰기/읽기
			String data = "hello world";
			os.write(data.getBytes("UTF-8"));
			
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer);
			
			data = new String(buffer, 0, readByteCount,"UTF-8");
			System.out.println("클라이언트가 말합니다.>" + data);
			*/
		}catch(IOException e){
			System.out.println("[클라이언트]에러:" + e);
		}
	}
}
