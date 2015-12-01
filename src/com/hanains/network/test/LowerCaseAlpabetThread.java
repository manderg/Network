package com.hanains.network.test;
public class LowerCaseAlpabetThread extends Thread {
	@Override
	public void run(){
		for(char c='a';c<='z';c++){
			System.out.print(c);
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
