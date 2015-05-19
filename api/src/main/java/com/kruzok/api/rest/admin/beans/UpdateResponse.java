package com.kruzok.api.rest.admin.beans;

import java.io.Serializable;

import com.kruzok.api.common.IResponse;

public class UpdateResponse implements IResponse, Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private String resposeData;

	public UpdateResponse() {
		super();
	}

	public UpdateResponse(String resposeData) {
		super();
		this.resposeData = resposeData;
	}

	public String getResposeData() {
		return resposeData;
	}

	public void setResposeData(String resposeData) {
		this.resposeData = resposeData;
	}

}