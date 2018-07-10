package com.light.springboot.message;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息接收者
 * @author 刘超
 * @date 2018/07/10
 */
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	private CountDownLatch latch;
	
	@Autowired
	public Receiver(CountDownLatch latch){
		this.latch = latch;
	}
	
	public void receiverMessage(String message){
		LOGGER.info("Received <" + message + ">");
		latch.countDown();
	}	
}
