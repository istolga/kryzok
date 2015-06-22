package com.kruzok.api.rest.activity.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.rest.activity.beans.SubscribeActivityRequest;
import com.kruzok.api.rest.activity.versioned.rev1.beans.SubscribeActivityRequestRev1;

@Component
public class SubscribeActivityRequestAdapterRev1 implements
		InputAdapter<SubscribeActivityRequestRev1, SubscribeActivityRequest> {

	private static final Log log = LogFactory
			.getLog(SubscribeActivityRequestAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public SubscribeActivityRequest adapt(SubscribeActivityRequestRev1 from) {

		SubscribeActivityRequest to = new SubscribeActivityRequest();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		BeanUtils.copyProperties(from, to);

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
	public Class<SubscribeActivityRequestRev1> getFromClass() {
		return SubscribeActivityRequestRev1.class;
	}

	@Override
	public Class<SubscribeActivityRequest> getToClass() {
		return SubscribeActivityRequest.class;
	}
}
