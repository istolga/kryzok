package com.kruzok.api.rest.share.beans;

public class ShareSetRequest {

	private static final long serialVersionUID = -2212995196237837540L;

	private String text;
	private int lifetime;
	private Boolean makePrivate;

	public ShareSetRequest() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public Boolean isPrivate() {
		return makePrivate;
	}

	public void setMakePrivate(Boolean makePrivate) {
		this.makePrivate = makePrivate;
	}
}