package net.mine.std.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class LogicBolt extends BaseBasicBolt{

	public LogicBolt(){
		System.out.println("创建了一次bolt——————" + Thread.currentThread().getName());
	}
	
	private int i = 0;
	
	public void execute(Tuple input, BasicOutputCollector collector) {
		
		String word = input.getStringByField("hello_field");
//		String word = (String) input.getValue(0);  
        String out = "I'm " + word +  "! ____" + Thread.currentThread().getName() + "____"+i++;  
        System.out.println("out=" + out);

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

}
