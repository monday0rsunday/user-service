package com.broship.user.service;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static final String API_PATH = "api.path";
	public static final String DB_RIAK_ADDRESS = "db.riak.address";
	public static final String HTTP_PORT = "http.port";

	public static String apiBasePath = "https://account.anyquiz.info";
	private static String dbRiakAddress = "127.0.0.1:8087";
	private static int httpPort = 8890;

	public static void load(InputStream is) throws Exception {
		Properties properties = new Properties();
		properties.load(is);
		apiBasePath = properties.getProperty(API_PATH, apiBasePath);
		dbRiakAddress = properties.getProperty(DB_RIAK_ADDRESS, dbRiakAddress);
		httpPort = Integer.parseInt(properties.getProperty(HTTP_PORT, httpPort + ""));
	}

	public static String getApiBasePath() {
		return apiBasePath;
	}

	public static String getDbRiakAddress() {
		return dbRiakAddress;
	}

	public static int getHttpPort() {
		return httpPort;
	}

}
