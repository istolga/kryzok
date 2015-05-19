package com.kruzok.api.rest.admin.beans;

import java.io.Serializable;

import com.kruzok.api.common.IResponse;

public class UpdateRequest implements IResponse, Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private String requestData;

	public UpdateRequest() {
		super();
	}

	public UpdateRequest(String requestData) {
		super();
		this.requestData = requestData;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

}