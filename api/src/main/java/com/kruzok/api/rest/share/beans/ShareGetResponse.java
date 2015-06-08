package com.kruzok.api.rest.share.beans;

import java.io.Serializable;


public class ShareGetResponse implements Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private String text;

	public ShareGetResponse() {
		super();
	}

	public ShareGetResponse(String text) {
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