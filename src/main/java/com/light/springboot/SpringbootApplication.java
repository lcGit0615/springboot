package com.light.springboot;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.light.springboot.message.Receiver;

@SpringBootApplication
public class SpringbootApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootApplication.class);
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, 
			MessageListenerAdapter listenerAdapter){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("msg"));
		return container;
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver){
		return new MessageListenerAdapter(receiver, "receiverMessage");
	}
	
	@Bean
	Receiver receiver(CountDownLatch latch){
		return new Receiver(latch);
	}
	
	@Bean
	CountDownLatch latch(){
		return new CountDownLatch(1);
	}
	
	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory){
		return new StringRedisTemplate(connectionFactory);
	}
	
	public static void main(String[] args) throws Exception{
		ApplicationContext ctx = SpringApplication.run(SpringbootApplication.class, args);
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
		LOGGER.info("Sending message...");
		template.convertAndSend("msg", "Hello World!");
		
		latch.await();
		
		System.exit(0);
	}

}
