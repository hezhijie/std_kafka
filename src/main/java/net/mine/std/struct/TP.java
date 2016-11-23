package net.mine.std.struct;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class TP {
	
	private int c;
	private AtomicInteger num = new AtomicInteger();
	private BlockingQueue<Runnable> runList;
	private AtomicInteger i = new AtomicInteger();
	
	public TP(int c){
		this.c = c;
		runList = new LinkedBlockingQueue<Runnable>();
	}
	
	public void exe(Runnable run){
		
		try {
			runList.put(run);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(num.getAndIncrement()<c){
			Thread t = new SimpleThread();
			t.setName("hehe-thread-"+i.getAndIncrement());
			t.start();
		}
	}
	
	
	class SimpleThread extends Thread{
		
		boolean running = true;
		public void run(){
			while(running){
				try {
					Runnable r = runList.take();
					r.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	} 
	
	public static void main(String args[]){
		TP t = new TP(5);
		
		for(int i=0;i<10;i++){
			Runnable run = new Runnable() {
				public void run() {
					try {
						
						System.out.println("this is in thread : " + Thread.currentThread().getName());
						
						Thread.sleep(1000*3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			t.exe(run);
		}
	}
	
}
