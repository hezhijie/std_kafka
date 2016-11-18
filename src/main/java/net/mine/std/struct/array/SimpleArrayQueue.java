package net.mine.std.struct.array;

// 未考虑线程安全,只做练习队列的写法
public class SimpleArrayQueue {

	public SimpleArrayQueue(){
		this(Integer.MAX_VALUE);
	}
	public SimpleArrayQueue(int length){
		list = new String[length];
	}
	
	
	private String[] list = null;  //队列
	
	private int take;  //take游标
	private int put;  //put游标
	
	private int count; //队列内元素数
	
	public void put(String e){
		
		if(count == list.length){
			System.out.println("队列满了,该清理了..,数据 " + e + " 被丢弃" );
			return;
		}
		
		list[put] = e;
		put++;
		count++;
		
		if(put == list.length)
			put = 0;
	}
	
	public String take(){
		if(count == 0){
			System.out.println("队列空了,还取个毛啊..");
			return null;
		}
		String e = list[take];
		list[take] = null;
		take++;
		count--;
		
		if(take == list.length)
			take = 0;
		
		return e;
	}
	
	public void read(){
		for(int i=0;i<list.length;i++){
			if(list[i] == null)
				continue;
			System.out.println("read : "+ i + " : " + list[i]);
		}
	}
	
	public static void main(String args[]){
		
		SimpleArrayQueue s = new SimpleArrayQueue(10);
		
		for(int i=0; i<8;i++){
			s.put("数"+i);
		}
		
		System.out.println("take 1 " + s.take());
		System.out.println("take 2 " + s.take());
		
		s.read();
		s.put("数***");
		s.put("数***1");
		System.out.println("take 3 " + s.take());
		s.put("数***2");
		
		s.read();
		
		
		
		
	}	
}
