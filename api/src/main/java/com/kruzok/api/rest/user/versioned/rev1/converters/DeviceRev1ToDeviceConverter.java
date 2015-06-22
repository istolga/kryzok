package com.kruzok.api.rest.user.versioned.rev1.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Device;
import com.kruzok.api.rest.user.versioned.rev1.beans.DeviceRev1;

@Component
public class DeviceRev1ToDeviceConverter implements
		Converter<DeviceRev1, Device> {

	@Override
	public Device convert(DeviceRev1 from) {
		Device to = new Device();

		BeanUtils.copyProperties(from, to);

		return to;
	}

}
