package com.ForMonk2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public final static String DB_HOST = "ds143603.mlab.com";
	public final static int DB_PORT = 43603;

	
	public final static class DB_CREDENTIALS {
		
		public final static String 	USERNAME = "android23235616";
		public final static String PASSWORD = "wart414ways465";
		public final static String DB_NAME = "infodb";
		public final static String USER1_CRED = "mongodb://android23235616:wart414ways465@ds143603.mlab.com:43603/infodb";
	}
	
	public final static class INSTAGRAM_CONSTANTS{
		
		public final static String BASE_URL = "https://api.instagram.com/oauth/authorize";
		
		public final static String ACCESS_TOKEN_BASE_URL = "https://api.instagram.com/oauth/access_token";
		
		public final static String CLIENT_ID = "3e7317c616f74b89840af96f97e7a56b";
		
		public final static String REDIRECT_URL = "http://localhost:8080/instaCallbackUrl";
		
		public final static String CLIENT_SECRET = "4057a80cc984462fbe47049b2a99fdd1";
		
		public final static String RESPONSE_TYPE = "code";
		
		public final static String SCOPE = "basic+comments+relationships+likes+follower_list";
		
		public final static String GRANT_TYPE = "authorization_code";
		
		public static Map<String, String> getAuthTokenQuery() {

	        return Collections.unmodifiableMap(new HashMap<String, String>() {
	         
				private static final long serialVersionUID = 1L;

				{
	               put("client_id" , CLIENT_ID);
	               put("redirect_uri" , REDIRECT_URL);
	               put("response_type" , RESPONSE_TYPE);
	               put("scope" , SCOPE) ;
	               
	            }
	        });
		
	
	}
	
	
	}

}
