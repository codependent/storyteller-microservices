package com.codependent.storyteller;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import rx.Observable;
import rx.schedulers.Schedulers;

@Service
public class ImageService {

	private static String[] images = {
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
	
	public Observable<String> getRandomImage(){
		return Observable.just(loadRandomImage())
				  .delay(500, TimeUnit.MILLISECONDS)
				  .subscribeOn(Schedulers.io());
	}
	
	private String loadRandomImage(){
		long random = Math.round(Math.random()*(images.length-1));
		return images[(int)random];
	}
	
}
