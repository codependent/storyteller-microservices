package com.codependent.storyteller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCommand;

import rx.Observable;
import rx.Single;

@RestController
@RequestMapping("/api")
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding(LogSource.class)
@RefreshScope
@SpringBootApplication
public class StoryTellerApiApplication {

	private final static String HTML = "<html><p>%s</p><img src='%s' style='height:150px'/></html>";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${storyteller-api-message}")
	private String message;
	
	@Value("${timing}")
	private Integer timing;
	
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
		logSource.output().send(MessageBuilder.withPayload(String.format("[%s] getRandomHtmlStory()", message)).build());
		
		return Observable.just(new String()).delay(timing, TimeUnit.MILLISECONDS).toSingle();
    }
	
	@RequestMapping(value="/stories", params={"random=true","format=html", "parallel=false"}, produces="text/html")
    public Single<String> getRandomHtmlStorySerialExecution() {
		logger.info("[{}] getRandomHtmlStory() - serial", message);
		logSource.output().send(MessageBuilder.withPayload(String.format("[%s] getRandomHtmlStory()", message)).build());
		
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
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(StoryTellerApiApplication.class).web(true).run(args);
    }
    
}
