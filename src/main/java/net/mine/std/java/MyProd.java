package net.mine.std.java;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MyProd {

	private final String TOPIC = "mine_topic";
	
	private static final Logger logger = LoggerFactory.getLogger(MyProd.class);
	
	public void execute(){
		
		Properties props = new Properties();
		
		props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", ByteArraySerializer.class.getName());
		
		Producer<String,byte[]> producer = new KafkaProducer<String, byte[]>(props);
		
		int i = 0;
		
		byte[] data = new byte[1024*256];
		
		while(i<10000){
			ProducerRecord<String,byte[]> record = new ProducerRecord<String, byte[]>(TOPIC, "kkkkk"+i, data);
			
			Future<RecordMetadata> result = producer.send(record);
			try {
				i++;
				RecordMetadata res = result.get();
				Gson gson=new Gson();
				logger.info("meta data is > " + gson.toJson(res));
				Thread.sleep(1000*1);
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
