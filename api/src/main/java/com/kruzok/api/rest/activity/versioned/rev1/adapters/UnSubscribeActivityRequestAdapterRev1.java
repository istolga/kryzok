package com.kruzok.api.rest.activity.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.rest.activity.beans.UnSubscribeActivityRequest;
import com.kruzok.api.rest.activity.versioned.rev1.beans.UnSubscribeActivityRequestRev1;

@Component
public class UnSubscribeActivityRequestAdapterRev1 implements
		InputAdapter<UnSubscribeActivityRequestRev1, UnSubscribeActivityRequest> {

	private static final Log log = LogFactory
			.getLog(SubscribeActivityRequestAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;


	@Override
	public UnSubscribeActivityRequest adapt(UnSubscribeActivityRequestRev1 from) {

		UnSubscribeActivityRequest to = new UnSubscribeActivityRequest();

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
	public Class<UnSubscribeActivityRequestRev1> getFromClass() {
		return UnSubscribeActivityRequestRev1.class;
	}

	@Override
	public Class<UnSubscribeActivityRequest> getToClass() {
		return UnSubscribeActivityRequest.class;
	}
}
