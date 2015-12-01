package com.hanains.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static final int PORT = 5353;
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
			
			//5. IOStream
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
					
			//6. 데이터 읽기
			try{
				byte[] buffer = new byte[256];
				while(true){
					int readByteCount = inputStream.read(buffer);
					if(readByteCount<0){
						System.out.println("[서버]클라이언트로부터 연결 끊김");
						break;
					}
					String data = new String(buffer, 0, readByteCount);
					System.out.println("[서버]수신 데이터:" + data);
					
					//7. 데이터 보내기
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
				}
			}catch(IOException e){
				System.out.println("[서버]에러:" + e);
			}finally{
				inputStream.close();
				outputStream.close();
				if(socket.isClosed()==false){
					socket.close();
				}
			}
			
			
			//8. 소켓 닫기
			inputStream.close();
			outputStream.close();
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
