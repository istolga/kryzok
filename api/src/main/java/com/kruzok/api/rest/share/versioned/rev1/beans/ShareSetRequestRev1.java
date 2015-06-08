package com.kruzok.api.rest.share.versioned.rev1.beans;

import java.io.Serializable;

public class ShareSetRequestRev1 implements Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private String text;
	private Integer lifetime;
	private Boolean makePrivate;

	public ShareSetRequestRev1() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLifetime() {
		return lifetime;
	}

	public void setLifetime(Integer lifetime) {
		this.lifetime = lifetime;
	}

	public Boolean getMakePrivate() {
		return makePrivate;
	}

	public void setMakePrivate(Boolean makePrivate) {
		this.makePrivate = makePrivate;
	}
}