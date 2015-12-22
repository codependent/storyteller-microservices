package com.codependent.storyteller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.HystrixCommand;

@FeignClient("stories-microservice")
public interface StoryServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/stories", params="random")
	HystrixCommand<String> getStory(@RequestParam("random") boolean random);
	
}
