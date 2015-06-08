package com.kruzok.api.rest.share.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.share.beans.ShareSetRequest;
import com.kruzok.api.rest.share.versioned.rev1.beans.ShareSetRequestRev1;

@Component
public class ShareSetRequestAdapterRev1 implements InputAdapter<ShareSetRequestRev1, ShareSetRequest> {

	private static final Log log = LogFactory.getLog(ShareSetRequestAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public ShareSetRequest adapt(ShareSetRequestRev1 from) {

		ShareSetRequest to = new ShareSetRequest();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}
		
		to.setText(from.getText());
		to.setLifetime(from.getLifetime());
		to.setMakePrivate(from.getMakePrivate());
		
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
	public Class<ShareSetRequestRev1> getFromClass() {
		return ShareSetRequestRev1.class;
	}

	@Override
	public Class<ShareSetRequest> getToClass() {
		return ShareSetRequest.class;
	}
}
