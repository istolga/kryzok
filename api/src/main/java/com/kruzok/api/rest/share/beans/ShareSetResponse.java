package com.kruzok.api.rest.share.beans;

import java.io.Serializable;

public class ShareSetResponse implements Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private Integer key;
	private Integer secretKey;

	public ShareSetResponse() {
		super();
	}

	public ShareSetResponse(Integer key) {
		super();
		this.key = key;
	}

	public ShareSetResponse(Integer key, Integer secretKey) {
		this(key);
		this.secretKey = secretKey;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(Integer secretKey) {
		this.secretKey = secretKey;
	}

}