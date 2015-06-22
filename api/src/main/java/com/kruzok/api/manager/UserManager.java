package com.kruzok.api.manager;

import com.kruzok.api.domain.User;

public interface UserManager {
	User findByUserIdAndDeviceId(String userId, String deviceId);
	
	void save(User user);
}
