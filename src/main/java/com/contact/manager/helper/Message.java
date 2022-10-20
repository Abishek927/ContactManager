package com.contact.manager.helper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString


public class Message {
	private String content;
	private String type;
	public Message(String content, String type) {
		super();
		this.content =content;
		this.type = type;
	}
	public Message() {
		super();
	}
	
	
	

}
