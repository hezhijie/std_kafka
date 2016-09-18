package net.mine.std.scala;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MyProducer {
	
	
	private String topic;
	private Producer<String, String> producer;
	
	public MyProducer(String topic){
		this.topic = topic;
		Properties props = new Properties();
		
		props.put("serializer.class", "kafka.serializer.StringEncoder");  
        props.put("metadata.broker.list", "localhost:9092"); 
		
		ProducerConfig config = new ProducerConfig(props);
		producer = new Producer<String,String>(config);
	}
	
	
	
	public void invokeOnce(String i){
		String message = "message once ";
		if(i!=null)
			message+=i;
		
		producer.send(new KeyedMessage<String, String>(topic,"key"+i,message));
	}
	
	public void invokeWheel(){
		int i = 0;
		while(true){
			invokeOnce(i+++"");
			try {
				Thread.sleep(1000*1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String args[]){
		MyProducer me = new MyProducer("mine_mine");
		me.invokeWheel();
		
		System.out.println("over..");
	}
}
