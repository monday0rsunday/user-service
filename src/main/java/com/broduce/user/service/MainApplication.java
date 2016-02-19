package com.broduce.user.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.broduce.user.service.resource.ConfigResource;
import com.broduce.user.service.resource.UserResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("/")
public class MainApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public MainApplication() {
		singletons.add(new UserResource(new UserDb(new Config())));
		singletons.add(new ConfigResource());
		singletons.add(new JacksonJsonProvider());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
