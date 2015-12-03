package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.ConsoleHandler;

public class ChatClientReceiveThread extends Thread {
	
	Socket socket = null;
	BufferedReader br = null;
	BufferedReader keyboard =null;
	
	public ChatClientReceiveThread(Socket socket, BufferedReader br) {
		this.socket = socket;
		this.br = br;
	}
	
	Console console =null;
	@Override
	public void run() {
		String line = null;
		try{
			while(true){
				line = br.readLine();
				System.out.println(line);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
