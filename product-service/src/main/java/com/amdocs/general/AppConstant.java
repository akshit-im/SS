package com.amdocs.general;

import java.util.Arrays;
import java.util.List;

public final class AppConstant {

	public final static Integer DEFAULT_RECORD_LIMIT = 100;
	public final static String AUTH_TYPE_BASIC = "Basic";
	public final static String AUTH_TYPE_BEARER = "Bearer";
	public final static String AUDIENCE = "Audience";

	public final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";
	public final static String JWT_AUTHORITIES = "authorities";

	public final static String[] CORS_ORIGINS_ARY = {"http://localhost:4200"};
	public final static String[] CORS_METHODS_ARY = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
	public final static String[] CORS_HEADERS_ARY = {"*"};

	public final static List<String> CORS_ORIGINS = Arrays.asList(CORS_ORIGINS_ARY);
	public final static List<String> CORS_METHODS = Arrays.asList(CORS_METHODS_ARY);
	public final static List<String> CORS_HEADERS = Arrays.asList(CORS_HEADERS_ARY);
}
