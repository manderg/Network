package com.hanains.network.test;

public class MultiThreadEx03 {
	public static void main(String args[]){
		Thread thread = new Thread(new DigitRunnableImpl());
		
		thread.start();
		for(char c ='A'; c<='Z'; c++){
			System.out.print(c);
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
