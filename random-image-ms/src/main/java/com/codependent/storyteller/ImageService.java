package com.codependent.storyteller;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

	public static String[] images = {
		"minion-carl.jpg",
		"minion-dave.jpg",
		"minion-evil.jpg",
		"minion-kevin.jpg",
		"minion-phil.jpg",
		"minion-stuart.jpg",
		"minion-tim.jpg",
		"minion-tom.jpg",
		"minion-wolverine.jpg"
	};
	
	public String getRandomImage(){
		long random = Math.round(Math.random()*(images.length-1));
		return images[(int)random];
	}
	
}
