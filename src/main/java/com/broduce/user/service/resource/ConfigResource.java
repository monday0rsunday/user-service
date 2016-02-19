package com.broduce.user.service.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.annotations.GZIP;

import com.broduce.user.service.Constant;
import com.broduce.user.service.model.Config;
import com.broduce.user.service.model.Message;

@Path("/")
public class ConfigResource {

	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/config/c")
	public Object createConfigOfUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		return new Message();
	}

	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/config/u")
	public Object updateConfigOfUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		return new Message();
	}

	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/config/d")
	public Object resetConfigOfUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		return new Message();
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/config/r")
	public Object getConfigOfUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		Config result = new Config();
		return new Message().addData("config", result);
	}
}
