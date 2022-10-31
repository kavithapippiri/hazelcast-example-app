package com.learning.hazelcast;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class AppConfig {
	
	@Bean
	public Config hazelCastConfig() {
		return new Config().setManagementCenterConfig(
				new ManagementCenterConfig().setConsoleEnabled(true));
				//.setUrl("http://localhost:8080/hazelcast-mancenter"));

	}
	
	@Bean
	public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
		return Hazelcast.newHazelcastInstance(hazelCastConfig);
	}

	@Bean("hazelcastMap") 
	public Map<String, String> accountMap(HazelcastInstance hazelcastInstance) {
		return hazelcastInstance.getMap("hazel-map");
	}
	
	@Bean("hazelcastLock") 
	Lock getLock(HazelcastInstance hazelcastInstance) {
		return hazelcastInstance.getCPSubsystem().getLock("hazel-lock");
	}

}
