package com.broship.user.service;

import java.util.HashMap;
import java.util.Map;

import com.broship.user.model.App;

public class MemAppDb implements IAppDb {

	private Map<String, App> appDb;

	public MemAppDb() {
		appDb = new HashMap<String, App>();
	}

	@Override
	public void updateApp(App app) {
		appDb.put(app.getId(), app);
	}

	@Override
	public App getApp(String id) {
		return appDb.get(id);
	}

}
