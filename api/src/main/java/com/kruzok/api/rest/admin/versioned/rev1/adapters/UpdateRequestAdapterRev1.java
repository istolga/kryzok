package com.kruzok.api.rest.admin.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.admin.beans.UpdateRequest;
import com.kruzok.api.rest.admin.versioned.rev1.beans.UpdateRequestRev1;

@Component
public class UpdateRequestAdapterRev1 implements
		OutputAdapter<UpdateRequestRev1, UpdateRequest> {

	private static final Log log = LogFactory
			.getLog(UpdateRequestAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public UpdateRequest adapt(UpdateRequestRev1 from) {

		UpdateRequest to = new UpdateRequest();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		to.setRequestData(from.getRequestData());

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
	public Class<UpdateRequestRev1> getFromClass() {
		return UpdateRequestRev1.class;
	}

	@Override
	public Class<UpdateRequest> getToClass() {
		return UpdateRequest.class;
	}
}
