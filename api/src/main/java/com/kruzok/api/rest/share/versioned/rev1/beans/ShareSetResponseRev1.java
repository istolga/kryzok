package com.kruzok.api.rest.share.versioned.rev1.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@XmlRootElement(name = "com.jigsaw.api.searchget.model.UserInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "key", "secretKey" })
@JsonPropertyOrder({ "key", "secretKey" })
public class ShareSetResponseRev1 implements Serializable {

	private static final long serialVersionUID = -2212995196237837540L;

	private Integer key;
	private Integer secretKey;

	public ShareSetResponseRev1() {
		super();
	}

	public ShareSetResponseRev1(Integer key) {
		super();
		this.key = key;
	}

	public ShareSetResponseRev1(Integer key, Integer secretKey) {
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
