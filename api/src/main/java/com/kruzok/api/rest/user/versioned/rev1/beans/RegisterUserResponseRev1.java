package com.kruzok.api.rest.user.versioned.rev1.beans;

import java.io.Serializable;

public class RegisterUserResponseRev1 implements Serializable {

	private static final long serialVersionUID = 1061386102235543158L;
	private String userId;
	private String deviceId;

	public RegisterUserResponseRev1() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}