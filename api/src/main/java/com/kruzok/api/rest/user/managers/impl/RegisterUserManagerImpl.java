package com.kruzok.api.rest.user.managers.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Device;
import com.kruzok.api.domain.User;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.manager.UserManager;
import com.kruzok.api.rest.user.beans.RegisterUserRequest;
import com.kruzok.api.rest.user.managers.RegisterUserManager;
import com.kruzok.api.util.GUIDGenerator;

@Component
public class RegisterUserManagerImpl implements RegisterUserManager {

	private static final Log log = LogFactory.getLog(RegisterUserManager.class);

	@Resource
	private UserManager userManager;

	@Override
	public User register(RegisterUserRequest request) throws ApiException {
		User user = new User();
		
		Device device = request.getDevice();
		device.setId(GUIDGenerator.generate());
		device.setCreateDate(Calendar.getInstance().getTime());
		
		user.addDevice(device);
		
		userManager.save(user);

		return user;
	}

}
