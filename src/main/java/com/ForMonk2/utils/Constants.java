package com.ForMonk2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public final static String DB_HOST = "localhost";
	public final static int DB_PORT = 27017;



	
	public final static class DB_CREDENTIALS {
		
		public final static String 	USERNAME = "InfluenceMonk";
		public final static String PASSWORD = "InfluenceMonk23235616";
		public final static String DB_NAME = "MonkDB";
		
	}
	
	public final static class INSTAGRAM_CONSTANTS{
		
		public final static String BASE_URL = "https://api.instagram.com/oauth/authorize";
		
		public final static String CLIENT_ID = "3e7317c616f74b89840af96f97e7a56b";
		
		public final static String REDIRECT_URL = "http://"+DB_HOST+":"+DB_PORT+"/callBackurl";
		
		public final static String RESPONSE_TYPE = "code";
		
		public final static String SCOPE = "basic+comments+relationships+likes+follower_list";
		
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
