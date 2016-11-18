package net.mine.std.storm.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class HelloSpout extends BaseRichSpout{

	private SpoutOutputCollector collector;
	
    private static String[] words = {"hello","world","hehe"};
	
    private int i  = 0;
    
    
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	public void nextTuple() {
		
		if(i++ > 10)
			return;
		
		
        String word = words[new Random().nextInt(words.length)]; 
        collector.emit("hellostrem",new Values(word + i + "___"+ Thread.currentThread().getName()));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declareStream("hellostrem", new Fields("hello_field"));
	}

}
