package com.codependent.micro;

import org.springframework.stereotype.Service;

@Service
public class StoryService {

	private static String[] stories = {
		"<p>Once upon a time there lived</p><p><img src='%s' style='height: 150px;'/></p>",
		"<p>In an Italian habour, at the foot of the mountain, lives our friend</p><p><img src='%s' style='height: 150px;'/></p>",
		"<p>A long time ago in a galaxy far, far away...</p><img src='%s' style='height: 150px;'/></p>",
	};
	
	public String getRandomStory(){
		long random = Math.round(Math.random()*(stories.length-1));
		return stories[(int)random];
	}
	
}
