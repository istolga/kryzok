package com.kruzok.api.rest.admin.upsert.beans;

public enum ActivityUpsertResponseState {

	ADDED((byte) 1, "Added"), UPDATED((byte) 5, "Updated"), ERROR((byte) 10,
			"Error");

	private byte id;
	private String description;

	ActivityUpsertResponseState(byte id, String description) {
		this.id = id;
		this.description = description;
	}

	public byte getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}