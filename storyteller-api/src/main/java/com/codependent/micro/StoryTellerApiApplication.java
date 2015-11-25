package com.codependent.micro;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class StoryTellerApiApplication {

	@Autowired
	private StoryGeneratorClient sgc;
	
	@RequestMapping(value="/stories", params="random=true")
    public String getRandomStory(HttpServletResponse response) {
		String randomStory = sgc.getRandomStory();
		response.setContentType("text/html");
		return randomStory;
    }
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(StoryTellerApiApplication.class).web(true).run(args);
    }
}
