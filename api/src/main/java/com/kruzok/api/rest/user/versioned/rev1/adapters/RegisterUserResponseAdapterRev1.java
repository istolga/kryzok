package com.kruzok.api.rest.user.versioned.rev1.adapters;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.user.beans.RegisterUserResponse;
import com.kruzok.api.rest.user.versioned.rev1.beans.RegisterUserResponseRev1;
import com.kruzok.api.rest.user.versioned.rev1.converters.DeviceRev1ToDeviceConverter;

@Component
public class RegisterUserResponseAdapterRev1 implements
		OutputAdapter<RegisterUserResponse, RegisterUserResponseRev1> {

	private static final Log log = LogFactory
			.getLog(RegisterUserResponseAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;
	@Resource
	private DeviceRev1ToDeviceConverter converter;

	@Override
	public RegisterUserResponseRev1 adapt(RegisterUserResponse from) {

		RegisterUserResponseRev1 to = new RegisterUserResponseRev1();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		to.setUserId(from.getUser().getId());
		to.setDeviceId(from.getUser().getDevices().get(0).getId());

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
	public Class<RegisterUserResponse> getFromClass() {
		return RegisterUserResponse.class;
	}

	@Override
	public Class<RegisterUserResponseRev1> getToClass() {
		return RegisterUserResponseRev1.class;
	}
}
