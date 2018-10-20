package com.ForMonk2.helpers;

import java.io.IOException;
import java.util.Map;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.NetworkHandler;

public class InstagramHelper {

		
	public static Object userLogin() {
		
		Map<String,String> queryMap = Constants.INSTAGRAM_CONSTANTS.getAuthTokenQuery();
		
		try {
			
			return NetworkHandler
					.getInstance()
					.sendGet(Constants.INSTAGRAM_CONSTANTS.BASE_URL
							, queryMap);
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
