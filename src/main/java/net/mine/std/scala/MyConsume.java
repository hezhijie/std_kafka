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

public class MyConsume {
	
	public void init(){
		Properties props = new Properties();  
//        props.put("group.id", groupid);  
//        props.put("consumer.id", consumerid);  
        props.put("zookeeper.connect", "localhost:2181");  
        props.put("zookeeper.session.timeout.ms", "60000");  
        props.put("zookeeper.sync.time.ms", "2000");  
        // props.put("auto.commit.interval.ms", "1000");  
  
        ConsumerConfig config = new ConsumerConfig(props);  
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(config); 
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("mine_mine", 2);
        
        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap =  connector.createMessageStreams(map);
        
        for(KafkaStream<byte[], byte[]> stream : streamMap.get("mine_mine")){
        	
        	ConsumerIterator<byte[], byte[]> streamIterator = stream.iterator();
        	while (streamIterator.hasNext()) {  
                MessageAndMetadata<byte[], byte[]> message = streamIterator.next();  
                String topic = message.topic();  
                int partition = message.partition();  
                long offset = message.offset();  
                String key = new String(message.key());  
                String msg = new String(message.message());  
                // 在这里处理消息,这里仅简单的输出  
                // 如果消息消费失败，可以将已上信息打印到日志中，活着发送到报警短信和邮件中，以便后续处理  
                System.out.println("thread : " + Thread.currentThread().getName()  
                        + ", topic : " + topic + ", partition : " + partition + ", offset : " + offset + " , key : "  
                        + key + " , mess : " + msg);  
            }
        }
        
	}
}
