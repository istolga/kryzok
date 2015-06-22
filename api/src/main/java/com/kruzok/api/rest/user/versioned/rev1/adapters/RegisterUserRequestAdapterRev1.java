package com.kruzok.api.rest.user.versioned.rev1.adapters;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.rest.user.beans.RegisterUserRequest;
import com.kruzok.api.rest.user.versioned.rev1.beans.RegisterUserRequestRev1;
import com.kruzok.api.rest.user.versioned.rev1.converters.DeviceRev1ToDeviceConverter;

@Component
public class RegisterUserRequestAdapterRev1 implements
		InputAdapter<RegisterUserRequestRev1, RegisterUserRequest> {

	private static final Log log = LogFactory
			.getLog(RegisterUserRequestAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;
	@Resource
	private DeviceRev1ToDeviceConverter converter;

	@Override
	public RegisterUserRequest adapt(RegisterUserRequestRev1 from) {

		RegisterUserRequest to = new RegisterUserRequest();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		to.setDevice(converter.convert(from.getDevice()));

		return to;
	}

	@Override
	public boolean canSupport(int version) {
		if (version <= latestVersion) {
			return true;
		}
		return false;
	}

	@Override
	public Class<RegisterUserRequestRev1> getFromClass() {
		return RegisterUserRequestRev1.class;
	}

	@Override
	public Class<RegisterUserRequest> getToClass() {
		return RegisterUserRequest.class;
	}
}
