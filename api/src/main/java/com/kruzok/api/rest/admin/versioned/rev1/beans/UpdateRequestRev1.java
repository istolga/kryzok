package com.kruzok.api.rest.admin.versioned.rev1.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@XmlRootElement
@JsonPropertyOrder({ "requestData" })
public class UpdateRequestRev1 implements Serializable {

	private static final long serialVersionUID = -6482568097305304398L;
	private String requestData;

	public UpdateRequestRev1() {
		super();
	}

	public UpdateRequestRev1(String requestData) {
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