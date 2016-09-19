package net.mine.std.java;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MyProd {

	private final String TOPIC = "mine_mine";
	
	private static final Logger logger = LoggerFactory.getLogger(MyProd.class);
	
	public void execute(){
		
		Properties props = new Properties();
		
		props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String,String> producer = new KafkaProducer<String, String>(props);
		
		int i = 0;
		
		while(i<4){
			ProducerRecord<String,String> record = new ProducerRecord<String, String>(TOPIC, "k"+i, "v"+i);
			
			Future<RecordMetadata> result = producer.send(record);
			try {
				i++;
				RecordMetadata data = result.get();
				Gson gson=new Gson();
				logger.info("meta data is > " + gson.toJson(data));
				Thread.sleep(1000*5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
//		producer.close();
	}
	
	public static void main(String args[]){
		MyProd prod = new MyProd();
		prod.execute();
		
		logger.info("over..");
	}
}
