package com.kruzok.api.common;

public enum MessageType {
	SUCCESS("success"), DANGER("danger"), WARNING("warning"), INFO("info");

	private String description;

	MessageType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
