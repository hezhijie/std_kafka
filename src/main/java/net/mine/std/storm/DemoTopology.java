package net.mine.std.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import net.mine.std.storm.bolt.LogicBolt;
import net.mine.std.storm.spout.HelloSpout;

public class DemoTopology {

	public static void main(String args[]) throws Exception{
		TopologyBuilder builder = new TopologyBuilder();   
        builder.setSpout("hellospout", new HelloSpout(),1);  
        
        LogicBolt bolt = new LogicBolt();
        
        builder.setBolt("logicbolt", bolt,2)
        		.shuffleGrouping("hellospout","hellostrem"); 
        Config conf = new Config();  
        conf.setDebug(false); 
        conf.setNumWorkers(2);
        
        
        System.out.println("bolt >>> "+bolt);
        
        if (args != null && args.length > 0) {  
            conf.setNumWorkers(3);  
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());  
        } else {  
  
            LocalCluster cluster = new LocalCluster();  
            cluster.submitTopology("firstTopo", conf, builder.createTopology());  
            Utils.sleep(3000);  
//            cluster.killTopology("firstTopo");  
//            cluster.shutdown();  
        }  
	}
}
