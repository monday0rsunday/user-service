package com.broship.user.service.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.broship.user.model.AccessToken;
import com.broship.user.model.Error;
import com.broship.user.model.Message;
import com.broship.user.model.RefreshToken;
import com.broship.user.model.User;
import com.broship.user.security.TokenDencoder;
import com.broship.user.service.Config;
import com.broship.user.service.Constant;
import com.broship.user.service.IUserDb;
import com.broship.user.service.model.AccessTokenResponse;
import com.broship.user.service.model.AvatarResponse;
import com.broship.user.service.model.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class UserResource {

	private static final Logger logger = Logger.getLogger(UserResource.class);
	private IUserDb userDb;

	public UserResource(IUserDb userDb) {
		this.userDb = userDb;
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/oauth/token")
	public Object signUpInUser(@Context HttpServletRequest req, @FormParam("grant_type") String grantType,
			@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("scope") String scope, @FormParam("format") String format, @FormParam("client_id") String appId,
			@FormParam("client_secret") String appSecret, @FormParam("action") String action,
			@FormParam("provider") String provider, @FormParam("device_id") String deviceId,
			@FormParam("device_os") String deviceOs, @FormParam("refresh_token") String refreshTokenStr,
			String formParam) {
		logger.debug("oauth\t" + formParam);
		switch (grantType) {
		case "password":
			AccessToken accessToken = new AccessToken();
			accessToken.setAppId(appId);
			accessToken.setDeviceId(deviceId);
			accessToken.setOs(deviceOs);
			accessToken.setProvider(provider);
			switch (provider) {
			case "facebook":
				User fbUser = getUserFromFacebook(username, password);
				break;
			case "internal":
				User inUser = new User();
				inUser.setUsername(username);
				inUser.setPassword(password);
				inUser.setProvider(provider);
				userDb.addUser(inUser);
				inUser = userDb.getUser(username, provider);
				accessToken.setUserId(inUser.getId());
				break;
			default:
				return new Message(new Error(1, "unknown provider " + provider));
			}
			return getAccessTokenResponse(accessToken);
		case "refresh_token":
			RefreshToken refreshToken = TokenDencoder.decodeRefreshToken(refreshTokenStr);
			AccessToken newAccessToken = TokenDencoder.decodeAccessToken(refreshToken.getAccessToken());
			return getAccessTokenResponse(newAccessToken);
		default:
			return new Message(new Error(1, "unknown grant type " + grantType));
		}
	}

	private User getUserFromFacebook(String fbUserId, String fbAccessToken) {
		User user = null;
		return user;
	}

	private AccessTokenResponse getAccessTokenResponse(AccessToken accessToken) {
		long currentTime = System.currentTimeMillis() / 1000;
		long expires = 24 * 60 * 60;
		String accessTokenStr = TokenDencoder.encodeAccessToken(accessToken);
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setAccessToken(accessTokenStr);
		refreshToken.setCreatedAt(currentTime);

		AccessTokenResponse response = new AccessTokenResponse();
		response.setAccessToken(accessTokenStr);
		response.setRefreshToken(TokenDencoder.encodeRefreshToken(refreshToken));
		refreshToken.setCreatedAt(currentTime);
		response.setScope(String.join(" ", accessToken.getScopeList()));
		response.setTokenType("Bearer");
		response.setUserId(accessToken.getUserId());
		response.setExpiresIn(expires);
		response.setExpiresAt(currentTime + expires);
		return response;
	}

	@GZIP
	@GET
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}")
	public Object getUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("access_token") String accessTokenStr) {
		logger.debug("get_user\t" + req.getRequestURI());
		AccessToken accessToken = TokenDencoder.decodeAccessToken(accessTokenStr);
		if (accessToken == null) {
			return new Message(new Error(1, "invalid access token"));
		}
		if (accessToken.getUserId() != userId) {
			return new Message(new Error(1, "access token of other user"));
		}
		User user = userDb.getUser(userId);
		if (user == null) {
			return new Message(new Error(1, "user " + userId + " does not exist"));
		}
		return new UserResponse(user);
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}")
	public Object updateUser(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("access_token") String accessTokenStr, String userStr) {
		logger.debug("update_user\t" + req.getRequestURI() + "\t" + userStr);
		AccessToken accessToken = TokenDencoder.decodeAccessToken(accessTokenStr);
		if (accessToken == null) {
			return new Message(new Error(1, "invalid access token"));
		}
		if (accessToken.getUserId() != userId) {
			return new Message(new Error(1, "access token of other user"));
		}
		User user = userDb.getUser(userId);
		if (user == null) {
			return new Message(new Error(1, "user " + userId + " does not exist"));
		}
		ObjectMapper om = new ObjectMapper();
		try {
			UserResponse us = om.readValue(userStr, UserResponse.class);
			if (us.getName() != null)
				user.setName(us.getName());
			if (us.getDob() != null) {
				try {
					user.setDob(UserResponse.dobFormat.parse(us.getDob()));
				} catch (Exception e) {
					logger.warn("parse date error ", e);
				}
			}
			if (us.getHob() > -1)
				user.setHob(us.getHob());
			if (us.getIdentity() != null)
				user.setIdentity(us.getIdentity());
			if (us.getGender() != null)
				user.setGender(us.getGender());
			if (us.getPhone() != null)
				user.setPhone(us.getPhone());
			if (us.getEmail() != null)
				user.setEmail(us.getEmail());
			userDb.updateUser(user);
		} catch (IOException e) {
			return new Message(new Error(1, e.getMessage()));
		}
		return new UserResponse(userDb.getUser(userId));
	}

	@GZIP
	@GET
	@Path("/user/{user_id}/avatar")
	@Produces("image/jpeg")
	public Object getUserAvatar(@Context HttpServletRequest req, @PathParam("user_id") long userId) {
		logger.debug("get_avatar\t" + req.getRequestURI());
		return userDb.getUserAvatar(userId);
	}

	@GZIP
	@POST
	@Produces(Constant.APP_JSON_UTF8_MEDIA_TYPE)
	@Path("/user/{user_id}/avatar")
	public Object updateUserAvatar(@Context HttpServletRequest req, @PathParam("user_id") long userId,
			@QueryParam("access_token") String accessTokenStr, MultipartFormDataInput input) {
		logger.debug("update_avatar\t" + req.getRequestURI());
		AccessToken accessToken = TokenDencoder.decodeAccessToken(accessTokenStr);
		if (accessToken == null) {
			return new Message(new Error(1, "invalid access token"));
		}
		if (accessToken.getUserId() != userId) {
			return new Message(new Error(1, "access token of other user"));
		}
		List<InputPart> inputParts = input.getFormDataMap().get("uploadedAvatar");
		if (inputParts.isEmpty()) {
			return new Message(new Error(1, "no uploadedAvatar"));
		}
		try {
			byte[] avatarBytes = IOUtils.toByteArray(inputParts.get(0).getBody(InputStream.class, null));
			// TODO convert image to jpeg
			userDb.updateUserAvatar(userId, avatarBytes);
			return new AvatarResponse(Config.getApiBasePath() + "/user/" + userId + "/avatar");
		} catch (IOException e) {
			return new Message(new Error(1, "error while processing uploadd avatar: " + e.getMessage()));
		}
	}
}
