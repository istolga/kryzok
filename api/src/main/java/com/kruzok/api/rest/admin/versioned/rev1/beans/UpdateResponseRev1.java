package com.kruzok.api.rest.admin.versioned.rev1.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@XmlRootElement
@JsonPropertyOrder({ "resposeData" })
public class UpdateResponseRev1 implements Serializable {

	private static final long serialVersionUID = -1168201816315752825L;
	private String resposeData;

	public UpdateResponseRev1() {
		super();
	}

	public UpdateResponseRev1(String resposeData) {
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
