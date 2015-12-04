package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<Writer> listWriters = new ArrayList<Writer>();
	private PrintWriter pw = null;
	private BufferedReader br = null;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters){
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		//IOStream
		try{
			InputStreamReader is = new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8);
			OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8);
						
			br = new BufferedReader(is);
			pw = new PrintWriter(os,true);
			
			while(true){
				String request = br.readLine();
				if(request==null){
					ChatServer.log("클라이언트로부터 연결 끊김");
					doQuit(pw);
					break;
				}
				
				String[] tokens = request.split(":");
				if("join".equals(tokens[0])){
					System.out.println("[info]preDojoin");
					doJoin(tokens[1],pw);
					System.out.println("[info]dojoin");
				}else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
					System.out.println("[info]message");
				}else if("quit".equals(tokens[0])){
					doQuit(pw);
				}else{
					ChatServer.log("알수없는 에러요청("+ tokens[0] + ")");
				}
				
			}
		}catch(IOException e){
			System.out.println("여기서 문제가발생하였음");
			e.printStackTrace();
		}
	}

	private void doQuit(Writer writer) throws IOException {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장 하였습니다.";
		ChatServer.log(data);
		broadcast(data+"\r\n");
	}
	
	private void removeWriter(Writer writer) throws IOException {
		socket.close();
		pw.close();
		br.close();
		System.out.println("제거하는 부분");
	}

	private void doMessage(String string) {
		String data = nickname + "님의 message : " + string;
		ChatServer.log(data);
		broadcast(data);
	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		
		String data = nickname + " 님이 참여하셨습니다.";
		ChatServer.log(nickname+ " 님이 참여하셨습니다.");	
		
		broadcast(data+"\r\n");
		
		addWriter(writer);
		
		pw.println("join:ok");
		pw.flush();
		
	}
	
	private void whisperTo(String nickname, Writer writer){
		
	}

	private void addWriter(Writer writer) {
		synchronized(listWriters){
			listWriters.add(writer);
		}
	}
	
	private void broadcast(String data){
		synchronized(listWriters){
			for(Writer writer:listWriters){
				if(writer==pw) continue;
				PrintWriter pw = (PrintWriter)writer;
				pw.println(data);
				pw.flush();
			}
		}
	}
	
	
}
