package com.kruzok.api.rest.activity.managers;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.activity.beans.SubscribeActivityRequest;
import com.kruzok.api.rest.activity.beans.UnSubscribeActivityRequest;

public interface SubscribeActivityManager {

	void subscribe(SubscribeActivityRequest request) throws ApiException;

	void unsubscribe(UnSubscribeActivityRequest request) throws ApiException;
}
