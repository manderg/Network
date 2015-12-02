package com.hanains.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class RequestHandler extends Thread {
	
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;
		
		try {
			// get IOStream
			bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			SimpleHttpServer.consolLog( "connected from " + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort() );

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			
			String request = "";
			SimpleHttpServer.consolLog("============================request infomation============================");
			while(true){
				String line = bufferedReader.readLine();
				if(line == null || "".equals(line)){
					break;
				}
				if("".equals(request)){
					request = line;
				}
				SimpleHttpServer.consolLog(line);
			}
			SimpleHttpServer.consolLog("==========================================================================");
			
			String[] tokens = request.split(" ");			
			if("GET".equals(tokens[0])){
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			}else{
				response400Error(outputStream, tokens[2]);
			}
			
		} catch( Exception ex ) {
			SimpleHttpServer.consolLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( bufferedReader != null ) {
					bufferedReader.close();
				}
				
				if( outputStream != null ) {
					outputStream.close();
				}
				
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				SimpleHttpServer.consolLog( "error:" + ex );
			}
		}
	}

	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException{
		//throws 를 해주면 이코드안에 IOException을 내가 캐치하지않고 나를 부르는 코드에 던져 버린다. 위의 코드에는 try로 넣어놨기때문에 처리한다.
		//default html 처리
		
		//url to home
		if(url.equals("/")){
			url = "/index.html";
		}		
		
		//File 객체 생성
		try{
			File file = new File("./webapp"+url);
			Path path = file.toPath();
			byte[] body = Files.readAllBytes(path);
			outputStream.write( body );
			//파일 존재 여부 체크
		}catch(NoSuchFileException e){
			response404Error(outputStream,protocol);
			return;
		}
	}

	private void response404Error(OutputStream outputStream, String protocol)throws IOException {
		
		File file = new File("./webapp/error/404.html");
		Path path = file.toPath();
		byte[] body = Files.readAllBytes(path);
		
		
		outputStream.write( body );
	}
	
private void response400Error(OutputStream outputStream, String protocol)throws IOException {
		
		File file = new File("./webapp/error/400.html");
		Path path = file.toPath();
		byte[] body = Files.readAllBytes(path);
		
		
		outputStream.write( body );
	}
}