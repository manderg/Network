package com.hanains.network.test;

import java.util.ArrayList;
import java.util.List;

public class DigitThread extends Thread {
	private List list = new ArrayList();
	
	public DigitThread(){}
	
	public DigitThread(List list){
		this.list = list;
	}
	@Override
	public void run() {
		for(int i=0;i<10;i++){
			System.out.print(i);
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
