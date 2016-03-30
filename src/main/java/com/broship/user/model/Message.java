package com.broship.user.model;

import java.util.HashMap;

public class Message extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 718583773247746186L;

	public Message() {
	}

	public Message(Error error) {
		put("error", error);
	}

	public Message addData(String key, Object value) {
		put(key, value);
		return this;
	}

}
