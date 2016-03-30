package com.broship.user.service.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.annotations.GZIP;

import com.broship.user.model.Config;
import com.broship.user.model.Message;
import com.broship.user.service.Constant;

@Path("/")
public class ConfigResource {

	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/apps/{app_id}/config")
	public Object updateConfigOfUser(@Context HttpServletRequest req, @PathParam("app_id") int appId) {
		return new Message();
	}

	@GZIP
	@GET
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/apps/{app_id}/config")
	public Object getConfigOfUser(@Context HttpServletRequest req, @PathParam("app_id") int appId) {
		Config result = new Config();
		return new Message().addData("config", result);
	}
}
