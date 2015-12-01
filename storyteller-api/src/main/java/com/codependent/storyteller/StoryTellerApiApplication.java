package com.codependent.storyteller;

import javax.servlet.http.HttpServletResponse;

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

@RestController
@RequestMapping("/api")
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@RefreshScope
@SpringBootApplication
public class StoryTellerApiApplication {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${storyteller-api-message}")
	private String message;
	
	@Autowired
	private StoryClient sgc;
	
	@RequestMapping(value="/stories", params="random=true")
    public String getRandomStory(HttpServletResponse response) {
		logger.info("[{}] getRandomStory()", message);
		String randomStory = sgc.getStory(true);
		response.setContentType("text/html");
		return randomStory;
    }
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(StoryTellerApiApplication.class).web(true).run(args);
    }
}
