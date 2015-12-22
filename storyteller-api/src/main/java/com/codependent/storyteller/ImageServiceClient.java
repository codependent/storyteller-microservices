package com.codependent.storyteller;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.HystrixCommand;

@FeignClient(name="images-microservice")
public interface ImageServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/images", params="random,fields")
	HystrixCommand<Map<String, String>> getImage(@RequestParam("random") boolean random, @RequestParam("fields") String fields);
	
}
