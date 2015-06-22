package com.kruzok.api.rest.user.beans;

import com.kruzok.api.common.IResponse;
import com.kruzok.api.domain.User;

public class RegisterUserResponse implements IResponse {
	private User user;

	public RegisterUserResponse() {
		super();
	}
	
	public RegisterUserResponse(User user) {
		this();
		setUser(user);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}