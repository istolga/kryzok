package com.kruzok.api.rest.share.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.share.beans.ShareSetResponse;
import com.kruzok.api.rest.share.versioned.rev1.beans.ShareSetResponseRev1;


@Component
public class ShareSetResponseAdapterRev1 implements OutputAdapter<ShareSetResponse, ShareSetResponseRev1> {

	private static final Log log = LogFactory.getLog(ShareSetResponseAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public ShareSetResponseRev1 adapt(ShareSetResponse from) {

		ShareSetResponseRev1 to = new ShareSetResponseRev1();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		to.setKey(from.getKey());
		to.setSecretKey(from.getSecretKey());

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
	public Class<ShareSetResponse> getFromClass() {
		return ShareSetResponse.class;
	}

	@Override
	public Class<ShareSetResponseRev1> getToClass() {
		return ShareSetResponseRev1.class;
	}
}
