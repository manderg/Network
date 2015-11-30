package com.hanains.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	public static void main(String[] args) { //왜 메인이 static일까 생각해보기!! static이 메모리상에 가장먼저올라가는데 main도 가장먼저올라가야할 부분이기때문?
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println("Host 이름 : " + inetAddress.getHostName());
			System.out.println("Host IP Address : " + inetAddress.getHostAddress());
			
			byte[] addresses = inetAddress.getAddress();
			for(int i=0;i<addresses.length;i++){
				//System.out.print(addresses[i]);
				System.out.print(addresses[i] & 0xff);
				if(i+1<addresses.length){
					System.out.print(".");
				}
			}
		}catch(UnknownHostException e){	
			e.printStackTrace(); //메모리사용량이많음 trace가 가능하도록하기위해 발생한지점을 저장하기 때문에
		}
	}
}
