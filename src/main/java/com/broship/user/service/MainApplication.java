package com.broship.user.service;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import com.basho.riak.client.api.RiakClient;
import com.broship.user.service.resource.AppResource;
import com.broship.user.service.resource.UserResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("/")
public class MainApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public MainApplication() {
		RiakClient riakClient = null;
		try {
			riakClient = RiakClient.newClient(Config.getDbRiakAddress());
		} catch (UnknownHostException e) {
			Logger.getLogger(getClass()).error("init riak client error", e);
		}
		singletons.add(new UserResource(new RiakUserDb(riakClient)));
		singletons.add(new AppResource(new MemAppDb()));
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
