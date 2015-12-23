package com.codependent.storyteller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rx.Observable;

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
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Observable<String> getRandomImage(){
		return Observable.create((s)->{
			logger.info("getRandomImage()");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			long random = Math.round(Math.random()*(images.length-1));
			s.onNext(images[(int)random]);
			s.onCompleted();
		});
		
	}
	
}
