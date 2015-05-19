package com.kruzok.api.rest.admin.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.admin.beans.UpdateResponse;
import com.kruzok.api.rest.admin.versioned.rev1.beans.UpdateResponseRev1;

@Component
public class UpdateResponseAdapterRev1 implements
		OutputAdapter<UpdateResponse, UpdateResponseRev1> {

	private static final Log log = LogFactory
			.getLog(UpdateResponseAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public UpdateResponseRev1 adapt(UpdateResponse from) {

		UpdateResponseRev1 to = new UpdateResponseRev1();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		to.setResposeData(from.getResposeData());

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
	public Class<UpdateResponse> getFromClass() {
		return UpdateResponse.class;
	}

	@Override
	public Class<UpdateResponseRev1> getToClass() {
		return UpdateResponseRev1.class;
	}
}
