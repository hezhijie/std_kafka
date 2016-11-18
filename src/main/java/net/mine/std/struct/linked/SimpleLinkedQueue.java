package net.mine.std.struct.linked;


/**
 * 简单链表队列   未考虑线程安全,只做练习队列的写法
 * 
 * @author user
 *
 */
public class SimpleLinkedQueue {

	class Node{
		public String v;
		public Node next;
		
		public Node(String v){
			this.v = v;
		}
	}
	Node init = new Node(null);
	
	Node head = init;
	Node tail = init;
	
	
	public void put(String v){
		
		Node node = new Node(v);
		tail.next = node;
		tail = node;
		
	}
	
	public String take(){
// 第一次实现,head 只是一个空的节点,不包含数据,head的next才是第一个数据节点 ，下面四行实现没考虑到
//		String v = head.v;
//		Node tempHead = head.next;
//		head = tempHead;
//		return v;
		
		/**
		 * 第二次实现
		 * 1. 取出第一个数据节点 
		 * 2. 把head节点指针指向第一个数据节点， 并切断老的head 和 first的联系 , head ->/ first -> second ->...
		 *  
		 */
		
		Node oldHead = head;
		Node first = head.next;  //第一个数据节点
		String v = first.v;
		head = first;
		head.v = null;
		oldHead.next = null;
		return v;
	}
	
	
	public void readFromHead(){
		
		System.out.println("head is :" + head.v);
		
		Node n = head.next;
		while(n != null){
			System.out.println(n.v);
			n = n.next;
		}
	}
	
	public static void main(String args[]){
		SimpleLinkedQueue q = new SimpleLinkedQueue();
		for (int i = 0; i < 10; i++) {
			q.put("data-"+i);
		}
		
		System.out.println("take 1 : " + q.take());
		System.out.println("take 2 : " + q.take());
		
		q.readFromHead();
		
	}
}
