package com.broship.user.security;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.broship.user.model.AccessToken;
import com.broship.user.model.RefreshToken;

/**
 * 
 * @author congnh
 *
 */
public class TokenDencoder {

	private static final Logger logger = Logger.getLogger(TokenDencoder.class);

	private static String accessTokenKey = "e75e2262ea460e5ae43be28d5b606d1a";
	private static String refreshTokenKey = "fh38ry39fh3ry93rfhfwjx0m30x20s0q";

	public static String encodeAccessToken(AccessToken accessToken) {
		String message = "" + accessToken.getUserId();
		message += "||" + accessToken.getDeviceId();
		message += "||" + String.join(" ", accessToken.getScopeList());
		message += "||" + accessToken.getExpiresIn();
		message += "||" + accessToken.getExpiresAt();
		message += "||" + accessToken.getProvider();
		message += "||" + accessToken.getOs();
		String rPrefix = RandomGenerator.generate(8);
		return rPrefix + XorDencoder.encode(XorDencoder.encode(message, accessTokenKey), rPrefix);
	}

	public static AccessToken decodeAccessToken(String token) {
		try {
			AccessToken accessToken = new AccessToken();
			String rPrefix = token.substring(0, 8);
			String message = XorDencoder.decode(XorDencoder.decode(token.substring(8), rPrefix), accessTokenKey);
			String[] fields = message.split("\\|\\|");
			int i = 0;
			if (fields.length >= 7) {
				accessToken.setUserId(Long.parseLong(fields[i++]));
				accessToken.setDeviceId(fields[i++]);
				String scopes = fields[i++];
				accessToken.getScopeList().addAll(Arrays.asList(scopes.split(" ")));
				accessToken.setExpiresIn(Long.parseLong(fields[i++]));
				accessToken.setExpiresAt(Long.parseLong(fields[i++]));
				accessToken.setProvider(fields[i++]);
				accessToken.setOs(fields[i++]);
				return accessToken;
			}
		} catch (Exception e) {
			logger.error("invalid token", e);
		}
		return null;
	}

	public static String encodeRefreshToken(RefreshToken refreshToken) {
		String message = "" + refreshToken.getAccessToken() + "||" + refreshToken.getCreatedAt();
		return XorDencoder.encode(message, refreshTokenKey);
	}

	public static RefreshToken decodeRefreshToken(String refreshTokenStr) {
		try {
			String message = XorDencoder.decode(refreshTokenStr, refreshTokenKey);
			String[] fields = message.split("\\|\\|");
			int i = 0;
			if (fields.length >= 2) {
				RefreshToken refreshToken = new RefreshToken();
				refreshToken.setAccessToken(fields[i++]);
				refreshToken.setCreatedAt(Long.parseLong(fields[i++]));
				return refreshToken;
			}
		} catch (Exception e) {
			logger.error("invalid token", e);
		}
		return null;
	}

}
