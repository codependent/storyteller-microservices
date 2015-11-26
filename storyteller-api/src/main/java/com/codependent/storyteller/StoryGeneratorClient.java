package com.codependent.storyteller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class StoryGeneratorClient {

	@Autowired
	private EurekaClient discoveryClient;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@HystrixCommand(fallbackMethod = "defaultStory")
	public String getRandomStory(){
		InstanceInfo ii = discoveryClient.getNextServerFromEureka("RANDOM-STORY-MICROSERVICE", false);
		String homePageUrl = ii.getHomePageUrl();
		String story = restTemplate.getForObject(homePageUrl+"/stories", String.class);
		return story;
	}
	
	public String defaultStory() {
        return "<html><body>No stories available</body></html>";
    }
	
}
