package com.codependent.storyteller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class StoryService {

	private static String[] stories = {
		"<p>Once upon a time there lived</p><p><img src='%s' style='height: 150px;'/></p>",
		"<p>In an Italian habour, at the foot of the mountain, lives our friend</p><p><img src='%s' style='height: 150px;'/></p>",
		"<p>A long time ago in a galaxy far, far away...</p><img src='%s' style='height: 150px;'/></p>",
	};

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EurekaClient discoveryClient;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@HystrixCommand(fallbackMethod="imageServiceNotAvailable")
	public Map<String, String> getRandomImage(){
		InstanceInfo ii = discoveryClient.getNextServerFromEureka("IMAGES-MICROSERVICE", false);
		String homePageUrl = ii.getHomePageUrl();
		Map<String, String> imageInfo = restTemplate.exchange(homePageUrl+"/images?random=true&fields=url", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String,String>>() {}, new Object[]{}).getBody();
		return imageInfo;
	}
	
	public String getRandomStory(){
		long random = Math.round(Math.random()*(stories.length-1));
		return stories[(int)random];
	}
	
	protected Map<String, String> imageServiceNotAvailable(){
		logger.warn("imageServiceNotAvailable()");
		Map<String, String> i = new HashMap<String, String>();
		i.put("imageUrl", "http://cl005067.mutua.es:9999/images/minion-phil.jpg");
		return i;
	}
}
