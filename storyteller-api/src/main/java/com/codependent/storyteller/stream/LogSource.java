package com.codependent.storyteller.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface LogSource{
	String OUTPUT = "rawLogMessages";
	@Output(LogSource.OUTPUT)
	MessageChannel output();
}
