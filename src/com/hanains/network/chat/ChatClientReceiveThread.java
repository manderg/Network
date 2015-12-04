package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.ConsoleHandler;

public class ChatClientReceiveThread extends Thread {
	
	Socket socket = null;
	BufferedReader br = null;
	String nickname = null;
	
	public ChatClientReceiveThread(Socket socket, BufferedReader br, String nickname) {
		this.socket = socket;
		this.br = br;
		this.nickname = nickname; 
	}
	
	Console console =null;
	@Override
	public void run() {
		String line = null;
		try{
			while(true){
				line = br.readLine();
				String[] originaltokens = line.split(":");
				if(originaltokens.length>1){
					String[] whispertokens = originaltokens[1].split("!");
					if(whispertokens.length==2 && whispertokens[0].equals(" " +nickname)==true){
						System.out.println(nickname + "님의 귓속말임 : " + line);
					}else if(whispertokens.length==2 && whispertokens[0].equals(" " +nickname)==false){
						continue;
					}else{
						System.out.println(line);
					}
				}else{
					System.out.println(line);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}	
}
