package net.mine.std.scala;

import kafka.producer.Partitioner;

public class DefaultPartitioner implements Partitioner{

	public int partition(Object key, int partionNum) {
		
		if(key instanceof Integer){
			int k = (Integer) key;
			return k%partionNum;
		}
		
		return key.hashCode()%partionNum;
		
	}

}
