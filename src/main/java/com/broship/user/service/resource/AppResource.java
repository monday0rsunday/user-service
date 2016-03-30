package com.broship.user.service.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.broship.user.model.App;
import com.broship.user.model.Error;
import com.broship.user.model.Message;
import com.broship.user.service.Constant;
import com.broship.user.service.IAppDb;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class AppResource {

	private static final Logger logger = Logger.getLogger(AppResource.class);
	private IAppDb appDb;

	public AppResource(IAppDb appDb) {
		this.appDb = appDb;
	}

	@POST
	@Path("/app/{app_id}")
	public Object updateApp(@Context HttpServletRequest req, @PathParam("app_id") String appId, String bodyStr) {
		logger.debug("update_app\t" + req.getRequestURI() + "\t" + bodyStr);
		ObjectMapper om = new ObjectMapper();
		try {
			App app = om.readValue(bodyStr, App.class);
			appDb.updateApp(app);
			return app;
		} catch (IOException e) {
			return new Message(new Error(1, e.getMessage()));
		}
	}

	@GET
	@Path("/app/{app_id}")
	public Object getApp(@Context HttpServletRequest req, @PathParam("app_id") String appId) {
		App app = appDb.getApp(appId);
		if (app == null)
			return new Message(new Error(1, "app " + appId + " not found"));
		return app;
	}

	@POST
	@Path("/app/{app_id}/v{version}/config")
	public Object updateAppAllConfig(@Context HttpServletRequest req, @PathParam("app_id") String appId,
			@PathParam("version") String version, String bodyStr) {
		return updateAppConfig(req, appId, version, "all", bodyStr);
	}

	@GET
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/app/{app_id}/v{version}/config")
	public Object getAppAllConfig(@Context HttpServletRequest req, @PathParam("app_id") String appId,
			@PathParam("version") String version) {
		return getAppConfig(req, appId, version, "all");
	}

	@POST
	@Path("/app/{app_id}/v{version}_{os}/config")
	public Object updateAppConfig(@Context HttpServletRequest req, @PathParam("app_id") String appId,
			@PathParam("version") String version, @PathParam("os") String os, String bodyStr) {
		logger.debug("update_app_config\t" + req.getRequestURI() + "\t" + bodyStr);
		App app = appDb.getApp(appId);
		if (app == null)
			return new Message(new Error(1, "app " + appId + " not found"));
		String versionKey = "v" + version + "_" + os;
		app.getVersionConfigMap().put(versionKey, bodyStr);
		return app.getVersionConfigMap().get(versionKey);
	}

	@GET
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/app/{app_id}/v{version}_{os}/config")
	public Object getAppConfig(@Context HttpServletRequest req, @PathParam("app_id") String appId,
			@PathParam("version") String version, @PathParam("os") String os) {
		logger.debug("get_app_config\t" + req.getRequestURI());
		App app = appDb.getApp(appId);
		if (app == null)
			return new Message(new Error(1, "app " + appId + " not found"));
		String versionKey = "v" + version + "_" + os;
		String config = app.getVersionConfigMap().get(versionKey);
		if (config == null) {
			config = app.getVersionConfigMap().get("v" + version + "_all");
		}
		if (config == null) {
			config = "{}";
		}
		return config;
	}
}
