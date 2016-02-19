package com.broduce.user.service.model;

import java.util.HashMap;

public class Message extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 718583773247746186L;

	private HashMap<String, Object> data;

	public Message() {
		data = new HashMap<String, Object>();
		put("data", data);
	}

	public Message(Error error) {
		data = new HashMap<String, Object>();
		put("data", data);
		put("error", error);
	}

	public Message addData(String key, Object value) {
		data.put(key, value);
		return this;
	}

}
