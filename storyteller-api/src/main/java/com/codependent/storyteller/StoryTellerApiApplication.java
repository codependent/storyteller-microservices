package com.codependent.storyteller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rx.Observable;
import rx.schedulers.Schedulers;

import com.netflix.hystrix.HystrixCommand;

@RestController
@RequestMapping("/api")
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@RefreshScope
@SpringBootApplication
public class StoryTellerApiApplication {

	private final static String HTML = "<html><p>%s</p><img src='%s' style='height:150px'/></html>";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${storyteller-api-message}")
	private String message;
	
	@Autowired
	private StoryServiceClient ssc;
	
	@Autowired
	private ImageServiceClient isc;
	
	@RequestMapping(value="/stories", params={"random=true","format=html"}, produces="text/html")
    public Observable<String> getRandomHtmlStory() {
		logger.info("[{}] getRandomHtmlStory()", message);
		
		//GET STORY
		HystrixCommand<String> randomStoryCommand = ssc.getStory(true);
		Observable<String> randomStory = randomStoryCommand.toObservable().subscribeOn(Schedulers.io());

		//GET IMAGE
		HystrixCommand<Map<String, String>>  randomImageCommand = isc.getImage(true, "url");
		Observable<Map<String, String>> randomImage = randomImageCommand.toObservable().subscribeOn(Schedulers.io());
		
		//COMPOSE AND PROCESS
		Observable<String> result = Observable.zip(randomStory, randomImage, (String story, Map<String, String> image) -> {
			return String.format(HTML, story, image.get("imageUrl"));
		});
		return result;
    }
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(StoryTellerApiApplication.class).web(true).run(args);
    }
}
