package com.kruzok.api.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Response<T> {

	private T data;
	private List<Message> messages;
	
	public Response() {
		super();
	}
	
	public Response(T data) {
		this();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void addMessages(List<Message> messages) {
		if (this.messages == null) {
			this.messages = new ArrayList<Message>();
		}
		this.messages.addAll(messages);
	}

	public void addMessages(Message... messages) {
		if (this.messages == null) {
			this.messages = new ArrayList<Message>();
		}
		this.messages.addAll(Arrays.asList(messages));
	}

	public boolean hasError() {
		if (this.messages == null) {
			return false;
		}
		for (Message message : messages) {
			if (message != null
					&& MessageType.DANGER.getDescription().equalsIgnoreCase(
							message.getType())) {
				return true;
			}
		}
		return false;
	}
}