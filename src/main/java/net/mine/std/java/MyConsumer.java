package net.mine.std.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MyConsumer {

	private static final Logger logger = LoggerFactory.getLogger(MyConsumer.class);
	private final String TOPIC = "mine_mine";
	
	public void execute(String groupid){
		Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", groupid);
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        List<String> topicList = new ArrayList<String>();
        topicList.add(TOPIC);
        consumer.subscribe(topicList);
        
        while(true){
        	ConsumerRecords<String, String> records = consumer.poll(100);
        	for(ConsumerRecord<String,String> record : records){
        		
        		Gson gson=new Gson();
        		logger.info("thread > "+Thread.currentThread().getName() +" ,groupid >" +groupid+"  ,record > " + gson.toJson(record));
        	}
        }
	}
	
	public static void main(String args[]) throws InterruptedException{
		final MyConsumer c = new MyConsumer();
		final MyConsumer c2 = new MyConsumer();
		
		new Thread("t1"){
			public void run(){
				c.execute("g1-1-1");
			}
		}.start();
		
		
		Thread.sleep(1000*2);
		
		new Thread("t2"){
			public void run(){
				c2.execute("g2-2-2");
			}
		}.start();
	}
}
