package com.ForMonk2.helpers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;

public class InstaCrawlerHelper {
	
	@SuppressWarnings("unchecked")
	public String getProfileData(String username) {
		String response = "";
		
		JSONObject resposneObj = new JSONObject();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);
		Gson gson = new Gson();
		JSONParser parser = new JSONParser();
		try {
			//String crawlerResposne = networkHandler.sendGet(Constants.CRAWLER_CONSTANTS.CRAWLER_URL, queries);
			resposneObj.put("result", "true");
			
			String crawlerResposne = networkHandler.sendGet(Constants.CRAWLER_CONSTANTS.CRAWLER_URL, queries);
			
			crawlerResposne = crawlerResposne.substring(0,  (crawlerResposne.length()-2));
			
			System.out.println("Response: " + crawlerResposne);
			JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
			//resposneObj.put("data", crawlerResponseObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resposneObj.put("result", "false");
			System.out.println("Exception 1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception 2");
		}
		
		response = resposneObj.toJSONString();
		return response;
	}
	
}
