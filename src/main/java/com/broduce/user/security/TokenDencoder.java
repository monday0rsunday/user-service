package com.broduce.user.security;

import org.apache.log4j.Logger;

import com.broduce.user.service.model.Session;

/**
 * 
 * @author congnh
 *
 */
public class TokenDencoder {

	private static String key = "e75e2262ea460e5ae43be28d5b606d1a";

	public static String encodeSession(Session session) {
		String message = "" + session.getUserId();
		message += "||" + session.getDeviceId();
		message += "||" + session.getCtime();
		message += "||" + session.getProvider();
		message += "||" + session.getOs();
		return XorDencoder.encode(message, key);
	}

	public static Session decodeSession(String token) {
		try {
			Session session = new Session();
			String message = XorDencoder.decode(token, key);
			String[] fields = message.split("\\|\\|");
			if (fields.length >= 5) {
				session.setUserId(Long.parseLong(fields[0]));
				session.setDeviceId(fields[1]);
				session.setCtime(Long.parseLong(fields[2]));
				session.setProvider(fields[3]);
				session.setOs(fields[4]);
				return session;
			}
		} catch (Exception e) {
			Logger.getLogger(TokenDencoder.class).error("", e);
		}
		return null;
	}

}
