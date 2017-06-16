package com.lmdestiny.redis;



import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestRedis {

	@SuppressWarnings("resource")
	@Test
	public void test() {
		//redis集群测试
		Set<HostAndPort> set =new HashSet<>();
		set.add(new HostAndPort("192.168.226.159", 7001));
		set.add(new HostAndPort("192.168.226.159", 7002));
		set.add(new HostAndPort("192.168.226.159", 7003));
		set.add(new HostAndPort("192.168.226.159", 7004));
		set.add(new HostAndPort("192.168.226.159", 7005));
		set.add(new HostAndPort("192.168.226.159", 7006));
		JedisCluster jedisCluster = new JedisCluster(set);
		jedisCluster.hset("hset", "key","你好");
		System.out.println(jedisCluster.hget("hset", "key"));
		//jedisCluster.close();
	}
	//redis整合spring
	@SuppressWarnings("resource")
	@Test
	public void test2(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");
		JedisCluster jedisCluster = ac.getBean(JedisCluster.class);
		jedisCluster.hset("hset", "key1","你好");
		System.out.println(jedisCluster.hget("hset", "key1"));
	}
	

}
