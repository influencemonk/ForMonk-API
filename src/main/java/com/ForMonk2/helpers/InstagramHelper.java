package com.ForMonk2.helpers;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.ForMonk2.model.AccessTokenResponseModel;
import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;




public class InstagramHelper {

		
	public static String userLogin() {
		
		Map<String,Object> queryMap = Constants.INSTAGRAM_CONSTANTS.getAuthTokenQuery();
		GeneralUtils.printStackTrace(queryMap.toString());
		
		try {
			
			return NetworkHandler
					.getInstance()
					.formatString
					(Constants.INSTAGRAM_CONSTANTS.BASE_URL
					, queryMap);		
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
	}
	
		
	public static String getAccessToken(String authCode) {
		
		GeneralUtils.printStackTrace(authCode);
		
		Map<String,String> query = new HashMap<String,String>();
		
		query.put("client_id", Constants.INSTAGRAM_CONSTANTS.CLIENT_ID);
		query.put("client_secret" , Constants.INSTAGRAM_CONSTANTS.CLIENT_SECRET);
		query.put("grant_type", Constants.INSTAGRAM_CONSTANTS.GRANT_TYPE);
		query.put("redirect_uri", Constants.INSTAGRAM_CONSTANTS.REDIRECT_URL);
		query.put("code", authCode);
		
		try {
			
			String response = NetworkHandler.getInstance().sendPOST(Constants.INSTAGRAM_CONSTANTS.ACCESS_TOKEN_BASE_URL, query);
			
			AccessTokenResponseModel responseObject = new Gson().fromJson(response, AccessTokenResponseModel.class);
			
			CollectionHandler.startOperation(responseObject, DBCollections.MonkDB, Operations.create);
			
			return response;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			return null;
		}
		
	}
	

	
	
}
