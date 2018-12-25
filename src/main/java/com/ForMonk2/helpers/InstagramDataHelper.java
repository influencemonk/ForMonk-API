package com.ForMonk2.helpers;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.CrawlerDataParser;
import com.ForMonk2.utils.NetworkHandler;


@SuppressWarnings("unchecked")
public class InstagramDataHelper {
	
	/**
	 * Method to get Profile Summary of a given user from Instagram GraphQL API
	 */
	public String getProfileSummary(String username, Integer maxPosts) {
		
		JSONObject responseObj = new JSONObject();
		

		JSONParser parser = new JSONParser();
		
		CrawlerDataParser crawlerParser = new CrawlerDataParser();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();

		if(maxPosts == null) {
			maxPosts = 12;
		}
		
		Map<String, Integer> queries = new HashMap<String, Integer>();
		queries.put("__a", 1);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());
		
		try {
			
			responseObj.put("result", "false");
			
			String crawlerResposne = "";
			try {
				crawlerResposne = networkHandler.sendGet(Constants.INSTA_SCRAPER.getUserProfileURL(username), queries, headers);
			}
			catch (RuntimeException e) {
				e.printStackTrace();
				responseObj.put("type", "invalid_username");
				responseObj.put("message", "Username not found!");
				return responseObj.toJSONString();
			}
			
			if(!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
			
			JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
			
			// Parse the received profile data:
			responseObj.put("data", crawlerParser.getPostSummaryInfo(crawlerResponseObj, maxPosts));
			responseObj.put("result", "true");
			}
			else {
				responseObj.put("result", "false");
				responseObj.put("type", "no_data");
				responseObj.put("message", "Failed to receive data!");
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
			responseObj.put("result", "false");
			responseObj.put("type", "error");
			System.out.println("IO Exception occured!");
		} catch (ParseException e) {
			responseObj.put("result", "false");
			responseObj.put("type", "error");
			System.out.println("Parse Exception occured!");
			e.printStackTrace();
		}

		
		System.out.println("response: "+ responseObj.toJSONString());
		return responseObj.toJSONString();


	}
	
	
	/**
	 * Method to get All Data for profile analytics of a given user from Instagram GraphQL API
	 */
	public String getFullProfileAnalytics(String username) {
		
		String response = "";
		
		JSONObject responseObj = new JSONObject();
		
		Map<String, Integer> queries = new HashMap<String, Integer>();
		queries.put("__a", 1);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());

		JSONObject profileInfoObj = new JSONObject();
		

		JSONParser parser = new JSONParser();
		
		CrawlerDataParser crawlerParser = new CrawlerDataParser();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		
		
		try {
				responseObj.put("result", "false");
				
				String crawlerResposne = "";
				try {
					crawlerResposne = networkHandler.sendGet(Constants.INSTA_SCRAPER.getUserProfileURL(username), queries, headers);
				}
				catch (RuntimeException e) {
					e.printStackTrace();

					responseObj.put("type", "invalid_username");
					responseObj.put("message", "Username not found!");
					return responseObj.toJSONString();
				}
				
				if(!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
					
					JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
					
					// Parse the received profile data:
					profileInfoObj = crawlerParser.getProfileInfoGraphQl(crawlerResponseObj);
			
					String instagramId = (String) profileInfoObj.get("id");
					boolean isPrivateProfile = (boolean) profileInfoObj.get("is_private");
					
					long totalLikes = -1;
					long totalComments = -1;
					
					if(!isPrivateProfile) {
						
						boolean postHasNextPage = false;
						String postEndCursor = null;
						
						
						JSONArray mainPostsArray = new JSONArray();
						
						do {
							String postsVariables = Constants.INSTA_SCRAPER.getPostsJsonVariables(instagramId, 50, postEndCursor);
							
							Map<String, String> postDataQueries = new HashMap<String, String>();
							postDataQueries.put("query_hash", Constants.INSTA_SCRAPER.QUERY_HASH_POSTS);
							postDataQueries.put("variables", postsVariables);
							
							Map<String, String> postDataHeaders = new HashMap<String, String>();
							postDataHeaders.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());
							
							System.out.println("POST COOKIE: "+Constants.INSTA_SCRAPER.getCookieVar2());
							String postsResponse = "";
							try {
							// Get all posts data of the user:
								postsResponse = networkHandler.sendGet(Constants.INSTA_SCRAPER.BASE_URL, postDataQueries, postDataHeaders);
							}
							catch (SocketException e) {
								e.printStackTrace();
								postHasNextPage = true;
								continue;
							}
							
							if(!postsResponse.equals("") || null != postsResponse) {
								
								if(responseObj.get("result").equals("false")) {
									responseObj.put("result", "true");
								}
								
								if(totalLikes == -1 || totalComments == -1) {
									totalLikes = 0;
									totalComments = 0;
								}
								
								JSONObject postResponseObj = (JSONObject) parser.parse(postsResponse);
								
								// Parse the received posts data:
								JSONObject postsInfoPage = crawlerParser.getFullProfileAnalytics(postResponseObj);
								
								postHasNextPage = (boolean) postsInfoPage.get("has_next_page");
								postEndCursor = (String) postsInfoPage.get("end_cursor");
								
		
								totalLikes += (long) postsInfoPage.get("page_total_likes");
								totalComments += (long) postsInfoPage.get("page_total_comments");
								
								JSONArray postsArray = (JSONArray) postsInfoPage.get("posts_data");
								
								
								 mainPostsArray.addAll(postsArray);
							}
						
						} while(postHasNextPage);
						
						profileInfoObj.put("posts_data", mainPostsArray);
						
					}
					else {
						profileInfoObj.put("posts_data", null);
					}	
					
					profileInfoObj.put("total_likes", totalLikes);
					profileInfoObj.put("total_comments", totalComments);
					
					System.out.println("PROFILE DATA: " + profileInfoObj.toJSONString());
					
				}
				else {
					responseObj.put("result", "false");
					responseObj.put("type", "no_data");
					responseObj.put("message", "Failed to receive data!");
				}

			
		} catch (IOException e) {
			e.printStackTrace();
			responseObj.put("result", "false");
			responseObj.put("type", "error");
			responseObj.put("message", "IO Exception occured!");
			System.out.println("IO Exception occured!");
			
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Parse Exception occured!");
			responseObj.put("result", "false");
			responseObj.put("type", "error");
			responseObj.put("message", "IO Exception occured!");
		}
		
			
		responseObj.put("data", profileInfoObj);
		
		response = responseObj.toJSONString();
		
		return response;
	}

}