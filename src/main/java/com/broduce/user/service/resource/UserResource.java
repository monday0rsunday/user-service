package com.broduce.user.service.resource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;

import com.broduce.user.security.TokenDencoder;
import com.broduce.user.service.Constant;
import com.broduce.user.service.UserDb;
import com.broduce.user.service.model.Error;
import com.broduce.user.service.model.Message;
import com.broduce.user.service.model.Session;
import com.broduce.user.service.model.User;
import com.broduce.user.service.model.rt.ChatResponse;
import com.broduce.user.service.model.rt.UserInfoResponse;
import com.broduce.user.service.model.rt.UserPublicInfoResponse;
import com.broduce.user.service.model.rt.SessionResponse;
import com.broduce.user.service.model.rt.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class UserResource {

	private static final Logger logger = Logger.getLogger(UserResource.class);
	private UserDb userDb;
	private ObjectMapper mapper;

	private SecureRandom random = new SecureRandom();

	public UserResource(UserDb userDb) {
		this.userDb = userDb;
		this.mapper = new ObjectMapper();
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/c")
	public Object addUser(@Context HttpServletRequest req, String bodyObj) {
		try {
			Map<String, Object> body = (Map<String, Object>) mapper.readValue(bodyObj, Map.class);
			if (!body.containsKey("user") || !body.containsKey("device")) {
				return new Message(new Error(1, "malform request, missing user/device"));
			}
			Map<String, String> userMap = (Map<String, String>) body.get("user");
			Map<String, String> deviceMap = (Map<String, String>) body.get("device");
			User registeringUser = new User();
			if (userMap.containsKey("name"))
				registeringUser.setName(userMap.get("name"));
			if (userMap.containsKey("first_name"))
				registeringUser.setFirstName(userMap.get("first_name"));
			if (userMap.containsKey("middle_name"))
				registeringUser.setMiddleName(userMap.get("middle_name"));
			if (userMap.containsKey("last_name"))
				registeringUser.setLastName(userMap.get("last_name"));
			if (userMap.containsKey("birthday"))
				registeringUser.setBirthday(userMap.get("birthday"));
			if (userMap.containsKey("email"))
				registeringUser.setEmail(userMap.get("email"));
			if (userMap.containsKey("phone"))
				registeringUser.setPhone(userMap.get("phone"));
			if (userMap.containsKey("gender"))
				registeringUser.setGender(userMap.get("gender"));
			if (userMap.containsKey("avatar_url"))
				registeringUser.setAvatarUrl(userMap.get("avatar_url"));
			if (!userMap.containsKey("username")) {
				return new Message(new Error(1, "malform request, missing user.username"));
			}
			if (!userMap.containsKey("password")) {
				return new Message(new Error(1, "malform request, missing user.password"));
			}
			if (!userMap.containsKey("provider")) {
				return new Message(new Error(1, "malform request, missing user.provider"));
			}
			if (!deviceMap.containsKey("id")) {
				return new Message(new Error(1, "malform request, missing device.id"));
			}
			if (!deviceMap.containsKey("os")) {
				return new Message(new Error(1, "malform request, missing device.os"));
			}
			registeringUser.setUsername(userMap.get("username"));
			registeringUser.setPassword(userMap.get("password"));
			registeringUser.setProvider(userMap.get("provider"));

			User existedUser = userDb.getUser(registeringUser.getUsername(), registeringUser.getProvider());
			User user = null;
			if (existedUser == null) {
				registeringUser.setChatUsername(
						registeringUser.getProvider().replaceAll("[^a-z0-9]", "") + registeringUser.getUsername());
				registeringUser.setChatPassword(new BigInteger(130, random).toString(8).substring(0, 16));
				user = userDb.addUser(registeringUser);
			} else {
				user = existedUser;
				// TODO check login
			}
			Session session = new Session();
			session.setUserId(user.getId());
			session.setProvider(user.getProvider());
			session.setDeviceId(deviceMap.get("id"));
			session.setOs(deviceMap.get("os"));
			session.setCtime(System.currentTimeMillis());
			return new Message().addData("user",
					new UserResponse(user.getId(), user.getName(), user.getFirstName(), user.getMiddleName(),
							user.getLastName(), user.getBirthday(), user.getEmail(), user.getPhone(), user.getGender(),
							user.getAvatarUrl(), user.getUsername(), user.getPassword(), user.getProvider(),
							new ChatResponse(user.getChatUsername(), user.getChatPassword()),
							new SessionResponse(TokenDencoder.encodeSession(session), session.getCtime())));
		} catch (Exception e) {
			logger.warn("", e);
			return new Message(new Error(500, e.getMessage()));
		}
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/session/u")
	public Object updateUserSession(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		try {
			Session session = TokenDencoder.decodeSession(sToken);
			long lastSignin = session.getCtime();
			if (session.getUserId() == userId) {
				session.setCtime(System.currentTimeMillis());
				User user = userDb.getUser(userId);
				return new Message().addData("user",
						new UserResponse(user.getId(), user.getName(), user.getFirstName(), user.getMiddleName(),
								user.getLastName(), user.getBirthday(), user.getEmail(), user.getPhone(),
								user.getGender(), user.getAvatarUrl(), user.getUsername(), user.getPassword(),
								user.getProvider(), new ChatResponse(user.getChatUsername(), user.getChatPassword()),
								new SessionResponse(TokenDencoder.encodeSession(session), lastSignin)));
			} else {
				return new Message(new Error(3, "incorrect session"));
			}
		} catch (Exception e) {
			logger.warn("", e);
			return new Message(new Error(2, e.getMessage()));
		}
	}

	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/session/d")
	public Object deleteUserSession(@Context HttpServletRequest req, @QueryParam("s") String sToken) {
		Session session = TokenDencoder.decodeSession(sToken);
		return new Message();
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/u")
	public Object updateUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken, Map<String, Object> body) {
		try {
			Session session = TokenDencoder.decodeSession(sToken);
			if (session.getUserId() == userId) {
				User user = userDb.getUser(userId);
				User updatedUser = userDb.updateUser(user);
				return new Message().addData("user",
						new UserInfoResponse(updatedUser.getId(), updatedUser.getName(), updatedUser.getFirstName(),
								updatedUser.getMiddleName(), updatedUser.getLastName(), updatedUser.getBirthday(),
								updatedUser.getEmail(), updatedUser.getPhone(), updatedUser.getGender(),
								updatedUser.getAvatarUrl(), updatedUser.getUsername(), updatedUser.getPassword(),
								updatedUser.getProvider()));
			} else {
				return new Message(new Error(3, "incorrect session"));
			}
		} catch (Exception e) {
			logger.warn("", e);
			return new Message(new Error(2, e.getMessage()));
		}
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/r")
	public Object getUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("s") String sToken) {
		try {
			Session session = TokenDencoder.decodeSession(sToken);
			if (session.getUserId() == userId) {
				User user = userDb.getUser(userId);
				return new Message().addData("user",
						new UserInfoResponse(user.getId(), user.getName(), user.getFirstName(), user.getMiddleName(),
								user.getLastName(), user.getBirthday(), user.getEmail(), user.getPhone(),
								user.getGender(), user.getAvatarUrl(), user.getUsername(), user.getPassword(),
								user.getProvider()));
			} else {
				User user = userDb.getUser(userId);
				return new Message().addData("user", new UserPublicInfoResponse(user));
			}
		} catch (Exception e) {
			logger.warn("", e);
			return new Message(new Error(2, e.getMessage()));
		}
	}
}
