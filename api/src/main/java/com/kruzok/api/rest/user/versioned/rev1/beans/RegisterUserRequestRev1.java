package com.kruzok.api.rest.user.versioned.rev1.beans;

import java.io.Serializable;

public class RegisterUserRequestRev1 implements Serializable {

	private static final long serialVersionUID = 805115990412959355L;
	private DeviceRev1 device;

	public RegisterUserRequestRev1() {
		super();
	}

	public DeviceRev1 getDevice() {
		return device;
	}

	public void setDevice(DeviceRev1 device) {
		this.device = device;
	}

}