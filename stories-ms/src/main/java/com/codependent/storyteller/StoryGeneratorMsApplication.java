package com.codependent.storyteller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import rx.Observable;
import rx.Single;

@RestController
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@RefreshScope
@SpringBootApplication
public class StoryGeneratorMsApplication {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${stories-ms-message}")
	private String message;
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping(value="/stories", params="random=true")
	public Single<String> getRandomStory(HttpServletResponse response) throws RestClientException, URISyntaxException{
		logger.info("[{}] generateHtml()", message);
		Observable<String> story = storyService.getRandomStory();
		return story.toSingle();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(StoryGeneratorMsApplication.class, args);
    }
}
