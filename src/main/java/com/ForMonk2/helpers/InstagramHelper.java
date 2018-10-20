package com.ForMonk2.helpers;

import java.io.IOException;
import java.util.Map;

import com.ForMonk2.models.ApiResponsModel;
import com.ForMonk2.models.InstagramModels;
import com.ForMonk2.models.InstagramModels.OAuthExceptionModel;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;

public class InstagramHelper {

		
	public static String userLogin() {
		
		Map<String,String> queryMap = Constants.INSTAGRAM_CONSTANTS.getAuthTokenQuery();
		
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
	
	
}
