package com.broship.user.service;

import com.broship.user.model.App;

public interface IAppDb {

	public void updateApp(App app);

	public App getApp(String id);
}
