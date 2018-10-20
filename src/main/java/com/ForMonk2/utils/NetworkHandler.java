

package com.ForMonk2.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;

public class NetworkHandler {
	
	private NetworkHandler() {
		
	}
	
	private static NetworkHandler instance;
	
	public static NetworkHandler getInstance() {
		
		if(instance == null ) {
			
			synchronized(NetworkHandler.class) {
				
				if(instance == null ) {
					
					instance = new NetworkHandler();
					
				}
				
			}
			
		}
		
		return instance;
		
	}
	
	public Object sendGet(String baseUrl , HashMap<String , String> queries) throws IOException {
		
		String queryString = "";
		
		if(queries != null ) {
			
			queryString = "?";
			
			for(String key : queries.keySet()) {
				
				queryString = key+"="+queries.get(key)+"&";
				
			}
			
			queryString = queryString.substring(0 , queryString.length() - 1);
			
			
		}
		
		URL mainUrl = new URL(baseUrl + queryString);
		
		GeneralUtils.printStackTrace("GET : " + mainUrl.toString());
		
		HttpURLConnection connection = (HttpURLConnection) mainUrl.openConnection();
		
		connection.setRequestMethod("GET");
		
		int responseCode = connection.getResponseCode();
		
		if(responseCode != 200) {
			
			throw new RuntimeException("GET request at "+mainUrl.toString() 
										+" \n responseCode : "+responseCode);
			
		}else {
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(connection.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response;
			
		}
		
	}
	
	
}
