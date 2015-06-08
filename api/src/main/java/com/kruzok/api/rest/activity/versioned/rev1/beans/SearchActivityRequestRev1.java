package com.kruzok.api.rest.activity.versioned.rev1.beans;

import java.io.Serializable;

public class SearchActivityRequestRev1 implements Serializable {

	private static final long serialVersionUID = 2064813120555014092L;
	private String title;

	public SearchActivityRequestRev1() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}