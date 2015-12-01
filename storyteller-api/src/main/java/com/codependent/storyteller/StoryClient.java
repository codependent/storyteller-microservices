package com.codependent.storyteller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("stories-microservice")
public interface StoryClient {

	@RequestMapping(method = RequestMethod.GET, value = "/stories", params="random")
    String getStory(@RequestParam("random") boolean random);
	
}
