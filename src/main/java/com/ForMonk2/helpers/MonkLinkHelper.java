package com.ForMonk2.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;

import com.ForMonk2.model.FacebookAuthResponse;
import com.ForMonk2.model.FacebookIDResponse;
import com.ForMonk2.model.FacebookPagesResponse;
import com.ForMonk2.model.InstagramBusinessAccountResponse;
import com.ForMonk2.model.InstagramMediaResponse;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;

public class MonkLinkHelper {

	public static FacebookAuthResponse getFacebookTokens(String code, Environment env) {
		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("client_id", env.getProperty("FACEBOOK.APP_ID"));
			params.put("client_secret", env.getProperty("FACEBOOK.SECRET"));
			params.put("redirect_uri", env.getProperty("FACEBOOK.REDIRECT_URL"));
			params.put("code", code);

			String response = NetworkHandler.getInstance().sendGet(Constants.GET_FACEBOOK_TOKEN_ENDPOINT, params);

			FacebookAuthResponse facebookAuthResponse = new Gson().fromJson(response, FacebookAuthResponse.class);

			return facebookAuthResponse;

		} catch (Exception e) {
			return null;
		}
	}

	public static FacebookIDResponse getFacebookAccountId(String authToken) {
		try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", authToken);
			map.put("fields", "id,name");
			map.put("format", "json");

			String response = NetworkHandler.getInstance()
					.sendGet(Constants.GRAPH_API.ENDPOINT + Constants.GRAPH_API.GET_ACCOUNT_ID, map);

			FacebookIDResponse facebookIDResponse = new Gson().fromJson(response, FacebookIDResponse.class);

			return facebookIDResponse;

		} catch (Exception e) {
			return null;
		}
	}

	public static FacebookPagesResponse getFacebookPages(String facebookUserId, String authToken) {

		try {
			String baseUrl = NetworkHandler.getInstance().formatBaseUrl(Constants.GRAPH_API.ENDPOINT, facebookUserId,
					"accounts");

			Map<String, String> params = new HashMap<String, String>();
			params.put("access_token", authToken);

			String response = NetworkHandler.getInstance().sendGet(baseUrl, params);

			return new Gson().fromJson(response, FacebookPagesResponse.class);

		} catch (Exception e) {
			return null;
		}

	}
	
	public static InstagramBusinessAccountResponse getInstaBusinessAccount(String instaAccountId , String accessToken) {
		
		try {
			String baseUrl = NetworkHandler.getInstance().formatBaseUrl(Constants.GRAPH_API.ENDPOINT, instaAccountId);
			
			Map<String , String> params = new HashMap<String , String>();
			params.put("fields", "instagram_business_account");
			params.put("access_token"  , accessToken);
			
			String response = NetworkHandler.getInstance().sendGet(baseUrl, params);
			
			
			return new Gson().fromJson(response, InstagramBusinessAccountResponse.class);
			
		}catch(Exception e ) {
			return null;
		}
		
		
	}
	
	public static InstagramMediaResponse getInstagramPosts(String instaBusinessAccountId , String authToken) {
		
		try {
			
			String baseUrl = NetworkHandler.getInstance().formatBaseUrl(Constants.GRAPH_API.ENDPOINT, instaBusinessAccountId);
			
			Map<String , String> params = new HashMap<String , String>();
			params.put("fields", Constants.GRAPH_API.FIELD);
			params.put("access_token" , authToken);
			
			String response = NetworkHandler.getInstance().sendGet(baseUrl, params);
			
			return new Gson().fromJson(response, InstagramMediaResponse.class);
			
		}catch(Exception e ) {
			return null;
		}
		
	}

}
