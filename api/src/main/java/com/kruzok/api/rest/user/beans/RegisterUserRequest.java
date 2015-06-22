package com.kruzok.api.rest.user.beans;

import com.kruzok.api.common.IRequest;
import com.kruzok.api.domain.Device;

public class RegisterUserRequest implements IRequest {
	private Device device;

	public RegisterUserRequest() {
		super();
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}