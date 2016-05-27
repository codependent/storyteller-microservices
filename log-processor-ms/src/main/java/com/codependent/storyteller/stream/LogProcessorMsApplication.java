package com.codependent.storyteller.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import com.codependent.storyteller.stream.LogProcessorMsApplication.LogProcessor;

@EnableEurekaClient
@EnableBinding(LogProcessor.class)
@SpringBootApplication
public class LogProcessorMsApplication {

	@Autowired
	private Environment env;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LogProcessor logProcessor;
	
	public static void main(String[] args) {
		SpringApplication.run(LogProcessorMsApplication.class, args);
	}
	
	/* #1 - Using injected LogProcessor 
	@StreamListener(LogProcessor.INPUT)
	public void processLog(LogMessage logMessage) {
		logger.info("[{}] - PROCESSING MESSAGE: [{}]", new Object[]{env.getActiveProfiles()[0]}, logMessage);
		logProcessor.output().send(MessageBuilder.withPayload(new LogMessage(logMessage.getText().toUpperCase())).build());
	}
	*/
	
	/* #2 - Using @StreamListener + @SendTo
	@StreamListener(LogProcessor.INPUT)
	@SendTo(LogProcessor.OUTPUT)
	public LogMessage processLog(LogMessage logMessage) {
		logger.info("[{}] - PROCESSING MESSAGE: [{}]", new Object[]{env.getActiveProfiles()[0]}, logMessage);
		return new LogMessage(logMessage.getText().toUpperCase());
	}*/
	
	/* #3 - Using @Transformer */
	@Transformer(inputChannel = LogProcessor.INPUT, outputChannel = LogProcessor.OUTPUT)
	public LogMessage processLog(LogMessage logMessage) {
		logger.info("[{}] - PROCESSING MESSAGE: [{}]", new Object[]{env.getActiveProfiles()[0]}, logMessage);
		return new LogMessage(logMessage.getText().toUpperCase());
	}
	
	public interface LogProcessor {
		String INPUT = "rawLogMessages";
		String OUTPUT = "processedLogMessages";
		
		@Input(LogProcessor.INPUT)
		SubscribableChannel input();
		
		@Output(LogProcessor.OUTPUT)
		MessageChannel output();
	}
}
