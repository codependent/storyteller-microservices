package com.codependent.storyteller;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.HystrixCommand;

@FeignClient("author-ms")
public interface AuthorServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/authors", params="random")
	HystrixCommand<Map<String, String>> getAuthor(@RequestParam("random") boolean random);
	
}
