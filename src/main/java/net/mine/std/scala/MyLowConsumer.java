package net.mine.std.scala;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class MyLowConsumer {

	private String groupid;  
    private String consumerid;
    private int threadPerTopic = 1;  //默认每个topic开启一个线程
    
    public MyLowConsumer(String groupid,String consumerid,Integer threadPerTopic){
    	this.groupid = groupid;
    	this.consumerid = consumerid;
    }
    
    public void consume() {  
    	 Properties props = new Properties();  
         props.put("group.id", groupid);  
         props.put("consumer.id", consumerid);  
         props.put("zookeeper.connect", "127.0.0.1:2181");  
         props.put("zookeeper.session.timeout.ms", "60000");  
         props.put("zookeeper.sync.time.ms", "2000");  
         
         ConsumerConfig config = new ConsumerConfig(props);  
         ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
         
         Map<String,Integer> topicCountMap = new HashMap<String,Integer>();
         topicCountMap.put("mine_mine", threadPerTopic);
         
         Map<String, List<KafkaStream<byte[], byte[]>>> streams= connector.createMessageStreams(topicCountMap);
         
         for(KafkaStream<byte[], byte[]> stream : streams.get("mine_mine")){
        	 new Worker(stream).start();;
         }
    }
    
    private class Worker extends Thread{
    	
    	private KafkaStream<byte[], byte[]> stream;
    	
    	public Worker(KafkaStream<byte[], byte[]> stream){
    		this.stream = stream;
    	}
		public void run() {
			execute(stream);
		}
    }
    
    
    private void execute(KafkaStream<byte[], byte[]> stream){
    	ConsumerIterator<byte[], byte[]> streamIterator = stream.iterator();
    	
    	while(streamIterator.hasNext()){
    		MessageAndMetadata<byte[], byte[]> message = streamIterator.next();
    		String topic = message.topic();  
            int partition = message.partition();  
            long offset = message.offset();  
            String key = new String(message.key());  
            String msg = new String(message.message());
            
            System.out.println("consumerid:" + consumerid + ", thread : " + Thread.currentThread().getName()  
                    + ", topic : " + topic + ", partition : " + partition + ", offset : " + offset + " , key : "  
                    + key + " , mess : " + msg);
    	}
    }
    
    
    public static void main(String args[]){
    	MyLowConsumer low = new MyLowConsumer("low_mm", "low_m_1", 1);
    	low.consume();
    }
    
    
}
