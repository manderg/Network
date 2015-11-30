package com.java.net;


import java.net.InetAddress;
import java.util.Scanner;

public class nslookup {
	public static void main(String args[]) throws Exception{
		
		Scanner scan = new Scanner(System.in);
				
		while(true){			
			System.out.print("nslookup>");
			String hostName = scan.nextLine();
			if(hostName.equals("exit")){
				break;
			}
			InetAddress[] address = InetAddress.getAllByName(hostName);
			
			for(InetAddress ad:address){
				System.out.println(ad);
			}			
		}
	}
}
