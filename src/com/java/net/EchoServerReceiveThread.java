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

public class EchoServerReceiveThread extends Thread {
	
	Socket socket = null;
	
	public EchoServerReceiveThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
			//4.연결성공
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[서버]연결됨 from " + remoteHostAddress + ":" + remoteHostPort);
			
			//5.IOStream
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line =  null;
			
			//6.데이터받기
			while((line = br.readLine())!=null){
				System.out.println("클라이언트로부터 :" + line);
				pw.println(line);
				pw.flush();
			}
			
			pw.close();
			br.close();
			socket.close();
			
			/*
			//6. 데이터 읽기
			byte[] buffer = new byte[256];
			while(true){
				int readByteCount = is.read(buffer);
				if(readByteCount<0){
					System.out.println("[서버]클라이언트로부터 연결 끊김");
					break;
				}
				String data = new String(buffer, 0, readByteCount);
				System.out.println("[서버]수신 데이터:" + data);
				
				//7. 데이터 보내기
				os.write(data.getBytes("UTF-8"));
				os.flush();
				
				is.close();
				os.close();
				if(socket.isClosed()==false){
					socket.close();
				}
				
			}  
			*/
		}catch(IOException e){
			System.out.println("[서버]에러:" + e);
		}
	}
}
