package com.kruzok.api.rest.activity.managers;

import java.util.List;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;

public interface SearchActivityManager {
	List<Activity> search(SearchActivityRequest request);
}
