package com.codependent.storyteller.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codependent.storyteller.client.ImageServiceClient;
import com.codependent.storyteller.client.StoryServiceClient;
import com.codependent.storyteller.stream.LogMessage;
import com.codependent.storyteller.stream.LogSource;
import com.netflix.hystrix.HystrixCommand;

import rx.Observable;
import rx.Single;

@RestController
@RequestMapping("/api")
public class StoryTellerApiController {

	private final static String HTML = "<html><p>%s</p><img src='%s' style='height:150px'/></html>";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${storyteller-api-message}")
	private String message;
	
	@Autowired
	private StoryServiceClient ssc;
	
	@Autowired
	private ImageServiceClient isc;
	
	@Autowired
	private LogSource logSource;
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void internalServerErrorHandler(Exception e){
		logger.error("{}",e.toString());
		logSource.output().send(MessageBuilder.withPayload(e.toString()).build());
	}
	
	@RequestMapping(value="/stories", params={"random=true","format=html"}, produces="text/html")
    public Single<String> getRandomHtmlStory() {
		logger.info("[{}] getRandomHtmlStory() - parallel", message);
		publishLog();		
		
		//GET STORY
		HystrixCommand<String> randomStoryCommand = ssc.getStory(true);
		Observable<String> randomStory = randomStoryCommand.observe();

		//GET IMAGE
		HystrixCommand<Map<String, String>>  randomImageCommand = isc.getImage(true, "url");
		Observable<Map<String, String>> randomImage = randomImageCommand.observe();
		
		//COMPOSE AND PROCESS
		Observable<String> result = Observable.zip(randomStory, randomImage, (String story, Map<String, String> image) -> {
			return String.format(HTML, story, image.get("imageUrl"));
		});
		
		return result.toSingle();
    }
	
	@RequestMapping(value="/stories", params={"random=true","format=html", "parallel=false"}, produces="text/html")
    public Single<String> getRandomHtmlStorySerialExecution() {
		logger.info("[{}] getRandomHtmlStory() - serial", message);
		publishLog();
		
		//GET STORY
		HystrixCommand<String> randomStoryCommand = ssc.getStory(true);
		 String randomStory = randomStoryCommand.execute();

		//GET IMAGE
		HystrixCommand<Map<String, String>>  randomImageCommand = isc.getImage(true, "url");
		Map<String, String> randomImage = randomImageCommand.execute();
		
		//COMPOSE AND PROCESS
		Observable<String> result = Observable.zip(Observable.just(randomStory), Observable.just(randomImage), (String story, Map<String, String> image) -> {
			return String.format(HTML, story, image.get("imageUrl"));
		});
		return result.toSingle();
    }
	
	private void publishLog(){
		LogMessage logMessage = new LogMessage(String.format("[%s] getRandomHtmlStory() [%f]", message, Math.random()));
		logSource.output().send(MessageBuilder.withPayload(logMessage).build());
	}
}
