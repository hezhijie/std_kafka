package net.mine.std.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

public class Test {
	
	private static final String url = "127.0.0.1:2181";
	
	public static void main(String args[]) throws Exception{
		CuratorFramework client = CuratorFrameworkFactory.builder()
									.connectString(url)
									.retryPolicy(new RetryOneTime(100))
									.connectionTimeoutMs(1000)
//									.namespace("elong_test")
									.build();
		
		String a = client.create().forPath("/elong_test/11");
		System.out.println(a);
		
	}
}
