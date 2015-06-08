package com.kruzok.api.common;

public class Message {
	String message;
	String type;

	public Message() {
		super();
	}

	public Message(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}

	public Message(String message, MessageType type) {
		super();
		this.message = message;
		if (type != null) {
			this.type = type.getDescription();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasError() {
		if (type == "error") {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}