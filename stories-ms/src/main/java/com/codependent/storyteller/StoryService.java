package com.codependent.storyteller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	private DiscoveryClient discoveryClient;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@HystrixCommand(fallbackMethod="imageServiceNotAvailable")
	public Map<String, String> getRandomImage(){
		List<ServiceInstance> instances = discoveryClient.getInstances("IMAGES-MICROSERVICE");
		String homePageUrl = null;
		if (instances != null && instances.size() > 0 ) {
			homePageUrl = instances.get(0).getUri().toString();
	    }
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
		i.put("imageUrl", "https://camo.githubusercontent.com/e871b5d002a9699e7a2d9fa0178af5c72f0743e0/68747470733a2f2f6e6574666c69782e6769746875622e636f6d2f487973747269782f696d616765732f687973747269782d6c6f676f2d7461676c696e652d3835302e706e67");
		return i;
	}
}
