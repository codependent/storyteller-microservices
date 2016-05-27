package com.codependent.storyteller.stream;

import java.io.Serializable;

public class LogMessage implements Serializable{

	private static final long serialVersionUID = -8332921150135521099L;

	private String text;

	public LogMessage() {}
	
	public LogMessage(String text) {
		super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}

