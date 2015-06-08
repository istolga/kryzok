package com.kruzok.api.rest.admin.upsert.beans;

import java.util.List;

import com.kruzok.api.common.Message;
import com.kruzok.api.domain.Activity;

public class ActivityUpsertResponse extends Activity {

	private ActivityUpsertResponseState responseState;

	private List<Message> messages;

	public ActivityUpsertResponse() {
		super();
	}
	
	public ActivityUpsertResponse(Activity activity) {
		super(activity);
	}

	public ActivityUpsertResponseState getResponseState() {
		return responseState;
	}

	public void setResponseState(ActivityUpsertResponseState responseState) {
		this.responseState = responseState;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}