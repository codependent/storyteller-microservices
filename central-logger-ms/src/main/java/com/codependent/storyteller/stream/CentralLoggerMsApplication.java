package com.codependent.storyteller.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.SubscribableChannel;

import com.codependent.storyteller.stream.CentralLoggerMsApplication.LogSink;

@EnableEurekaClient
@EnableBinding(LogSink.class)
@SpringBootApplication
public class CentralLoggerMsApplication {

	@Autowired
	private Environment env;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(CentralLoggerMsApplication.class, args);
	}
	
	@StreamListener(LogSink.INPUT)
	public void processLog(String logMessage) {
		logger.info("[{}] - RECEIVED LOG MESSAGE: [{}]", new Object[]{env.getActiveProfiles()}, logMessage);
	}
	
	public interface LogSink {
		String INPUT = "logMessages";
		@Input(LogSink.INPUT)
		SubscribableChannel input();
	}
}
