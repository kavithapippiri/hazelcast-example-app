package com.learning.hazelcast;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.core.HazelcastInstance;

@Configuration
@SpringBootApplication
public class HazelcastExampleAppApplication {
	
	
	@Autowired
	HazelcastInstance hazelcastInstance;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        Lock hazelcastLock = (Lock) context.getBean("hazelcastLock");
        Map<String,String> hazelcastMap =  (Map<String,String>)context.getBean("hazelcastMap");
        	
		SpringApplication.run(HazelcastExampleAppApplication.class, args);
		try {
        	hazelcastLock.lock();
        	if(null==hazelcastMap.get("applicationStatus") || !"RUNNING".equals(hazelcastMap.get("applicationStatus"))) {
        		System.out.println("We are started!");
        		hazelcastMap.put("applicationStatus","RUNNING");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			hazelcastLock.unlock();
		}	
	}

}
