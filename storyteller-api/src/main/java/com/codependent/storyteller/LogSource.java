package com.codependent.storyteller;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface LogSource{
	String OUTPUT = "logMessages";
	@Output(LogSource.OUTPUT)
	MessageChannel output();
}
