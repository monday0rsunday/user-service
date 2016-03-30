package com.broship.user.service;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static final String DB_RIAK_HOST = "db.riak.host";
	public static final String DB_RIAK_PORT = "db.riak.port";

	public static String basePath = "https://account.anyquiz.info";

	public static void load(InputStream is) throws Exception {
		Properties properties = new Properties();
		properties.load(is);
	}

	public static String getBasePath() {
		return basePath;
	}

}
