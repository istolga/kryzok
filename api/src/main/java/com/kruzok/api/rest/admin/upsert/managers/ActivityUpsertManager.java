package com.kruzok.api.rest.admin.upsert.managers;

import java.util.List;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponse;

public interface ActivityUpsertManager {
	List<ActivityUpsertResponse> upsert(List<Activity> activities);
}
