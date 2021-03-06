package com.ForMonk2.helpers;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
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
public class InstaCrawlerHelper {
	
	/**
	 * Method to get every post and comment data of a user
	 */
	public String getProfileData(String username) {
		String response = "";
		
		JSONObject resposneObj = new JSONObject();

		JSONObject profileInfoObj = new JSONObject();

		JSONParser parser = new JSONParser();
		
		CrawlerDataParser crawlerParser = new CrawlerDataParser();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);
		try {
			resposneObj.put("result", "true");
			
			// Get profile info from PHP crawler:
			String crawlerResposne = networkHandler.sendGet(Constants.CRAWLER_CONSTANTS.CRAWLER_URL, queries, null);
			
			if(!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
				
				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
				
				// Parse the received profile data:
				profileInfoObj = crawlerParser.getProfileInfo(crawlerResponseObj);
	
				
				String userId = (String) profileInfoObj.get("id");
				boolean postHasNextPage = false;
				String postEndCursor = null;
				
				int counter = 0;
				long mediaCount = (long) profileInfoObj.get("media_count");
				
				JSONArray mainPostsArray = new JSONArray();
				
				do {
					String postsVariables = Constants.INSTA_SCRAPER.getPostsJsonVariables(userId, 50, postEndCursor);
					
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
						continue;
					}
					
					if(!postsResponse.equals("") || null != postsResponse) {
						
						JSONObject postResponseObj = (JSONObject) parser.parse(postsResponse);
						
						// Parse the received posts data:
						JSONObject postsInfoPage = crawlerParser.getPostsInfo(postResponseObj);
						//System.out.println("POST DATA: " + postsInfoPage.toJSONString());
						postHasNextPage = (boolean) postsInfoPage.get("has_next_page");
						postEndCursor = (String) postsInfoPage.get("end_cursor");
						
						JSONArray postsArray = (JSONArray) postsInfoPage.get("posts_data");
						
						int previous = 0;
						
						// Get comments of every received post:
						for(int i = 0; i < postsArray.size(); i++) {
							previous = i-1;
							System.out.println("Proceesing post "+(counter+i+1)+" of "+mediaCount+"...");
							
							//Constants.INSTA_SCRAPER.COOKIE_VAR_INDEX = 1;
							
							JSONObject postObj = (JSONObject) postsArray.get(i);
							String shortcode = (String) postObj.get("shortcode");
							boolean hasNextPage = false;
							String endCursor = null;
							
							JSONArray mainCommentsArr = new JSONArray();
							
							resume_label: do {
								String commentsVariables = Constants.INSTA_SCRAPER.getCommentsJsonVariables(shortcode, 50, endCursor);
								
								Map<String, String> commentsDataQueries = new HashMap<String, String>();
								commentsDataQueries.put("query_hash", Constants.INSTA_SCRAPER.QUERY_HASH_COMMENTS);
								commentsDataQueries.put("variables", commentsVariables);
								
		
								Map<String, String> commentsDataHeaders = new HashMap<String, String>();
								commentsDataHeaders.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());
								
								System.out.println("Cookie used: " + Constants.INSTA_SCRAPER.getCookieVar2());
								
								String commentsResponse = "";
								
								try {
								// Get comments for current post:
									commentsResponse = networkHandler.sendGet(Constants.INSTA_SCRAPER.BASE_URL, commentsDataQueries, commentsDataHeaders);
									JSONObject commentResponseObj = (JSONObject) parser.parse(commentsResponse);
									JSONObject commentsInfoPage = crawlerParser.getCommentsInfo(commentResponseObj);
									
									//System.out.println("COMMENTS: " + commentsInfoPage.toJSONString());
									
									mainCommentsArr.addAll((JSONArray)commentsInfoPage.get("comments_data"));
									
									hasNextPage = (boolean) commentsInfoPage.get("has_next_page");
									endCursor = (String) commentsInfoPage.get("end_cursor");
								}
								catch(RuntimeException e) {
									System.out.println("Error: " + e.getMessage());
									String errorMsg = e.getMessage();
									
									/*String responseCodeStr = "";
									
									for(int index = 0; i < errorMsg.length(); index++) {
										if(errorMsg.charAt(index) == '#') {
											responseCodeStr = errorMsg.substring(index+1);
											break;
										}
									}*/
									
									//System.out.println("Response code: " + responseCodeStr);
									if(errorMsg.endsWith("429")) {
										if(Constants.INSTA_SCRAPER.COOKIE_VAR_INDEX < (Constants.INSTA_SCRAPER.cookie.length-1))
											Constants.INSTA_SCRAPER.COOKIE_VAR_INDEX++;
										else
											Constants.INSTA_SCRAPER.COOKIE_VAR_INDEX = 0;
										
										System.out.println("Previosly at comments Pos: "+ previous);
										if(previous < i) {
											i--;
										}
										//i--;
										
										System.out.println("Cookie var index: " + Constants.INSTA_SCRAPER.COOKIE_VAR_INDEX);
										System.out.println("Currently at comments Pos: "+ i);
										continue resume_label;
									}
								}
								catch(SocketException e) {
									e.printStackTrace();
									continue;
								}
								catch(ParseException e) {
									e.printStackTrace();
									continue;
								}
								
								
							} while(hasNextPage);
							
							//System.out.println("ALL COMMENTS: " + mainCommentsArr.toJSONString());
							
							postObj.put("comments_data", mainCommentsArr);
			
						}
						
						counter += postsArray.size();
						
						 mainPostsArray.addAll(postsArray);
					}
					
				
				} while(postHasNextPage);
				
				profileInfoObj.put("posts_data", mainPostsArray);
				
				System.out.println("PROFILE DATA: " + profileInfoObj.toJSONString());
			}
			else {
				resposneObj.put("result", "false");
				resposneObj.put("message", "Failed to receive data!");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			resposneObj.put("result", "false");
			System.out.println("IO Exception occured!");
		} catch (ParseException e) {
			System.out.println("Parse Exception occured!");
			e.printStackTrace();
		}
		
		
		
		resposneObj.put("data", profileInfoObj);
		
		response = resposneObj.toJSONString();
		
		return response;
	}
	
	
	/**
	 * Method to get all POSTS data of a user
	 */
	public String getPostsData(String username) {
		String response = "";
		
		JSONObject resposneObj = new JSONObject();

		JSONObject profileInfoObj = new JSONObject();

		JSONParser parser = new JSONParser();
		
		CrawlerDataParser crawlerParser = new CrawlerDataParser();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);
		try {
			resposneObj.put("result", "true");
			
			// Get profile info from PHP crawler:
			String crawlerResposne = networkHandler.sendGet(Constants.CRAWLER_CONSTANTS.CRAWLER_URL, queries, null);
			
			if(!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
				
				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
				//System.out.println("Response: " + crawlerResponseObj.toJSONString());
				
				// Parse the received profile data:
				profileInfoObj = crawlerParser.getProfileInfo(crawlerResponseObj);
	
				
				String userId = (String) profileInfoObj.get("id");
				boolean postHasNextPage = false;
				String postEndCursor = null;
				
				/*int counter = 0;
				long mediaCount = (long) profileInfoObj.get("media_count");*/
				
				JSONArray mainPostsArray = new JSONArray();
				
				do {
					String postsVariables = Constants.INSTA_SCRAPER.getPostsJsonVariables(userId, 50, postEndCursor);
					
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
						continue;
					}
					
					if(!postsResponse.equals("") || null != postsResponse) {
						
						JSONObject postResponseObj = (JSONObject) parser.parse(postsResponse);
						
						// Parse the received posts data:
						JSONObject postsInfoPage = crawlerParser.getPostsInfo(postResponseObj);
						//System.out.println("POST DATA: " + postsInfoPage.toJSONString());
						postHasNextPage = (boolean) postsInfoPage.get("has_next_page");
						postEndCursor = (String) postsInfoPage.get("end_cursor");
						
						JSONArray postsArray = (JSONArray) postsInfoPage.get("posts_data");
						
						
						 mainPostsArray.addAll(postsArray);
					}
					
				
				} while(postHasNextPage);
				
				profileInfoObj.put("posts_data", mainPostsArray);
				
				System.out.println("PROFILE DATA: " + profileInfoObj.toJSONString());
			}
			else {
				resposneObj.put("result", "false");
				resposneObj.put("message", "Failed to receive data!");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			resposneObj.put("result", "false");
			System.out.println("IO Exception occured!");
		} catch (ParseException e) {
			System.out.println("Parse Exception occured!");
			e.printStackTrace();
		}
		
		
		
		resposneObj.put("data", profileInfoObj);
		
		response = resposneObj.toJSONString();
		
		return response;
	}
	
	
	/*
	 * Method to get instant instagram profile data of top 12 posts of a user 
	 */
	public JSONObject getInstantProfileData(String username) {
		
		JSONObject responseObj = new JSONObject();
		

		JSONParser parser = new JSONParser();
		
		CrawlerDataParser crawlerParser = new CrawlerDataParser();
		
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);
		
		
		try {
			
			// Get profile info from PHP crawler:
			String crawlerResposne = networkHandler.sendGet(Constants.CRAWLER_CONSTANTS.CRAWLER_URL, queries, null);
			
			if(!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
			
			JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
			//System.out.println("Response: " + crawlerResponseObj.toJSONString());
			
			// Parse the received profile data:
			responseObj.put("data", crawlerParser.getInstantProfileInfo(crawlerResponseObj));
			responseObj.put("result", "true");
			}
			else {
				responseObj.put("result", "false");
				responseObj.put("message", "Failed to receive data!");
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
			responseObj.put("result", "false");
			System.out.println("IO Exception occured!");
		} catch (ParseException e) {
			responseObj.put("result", "false");
			System.out.println("Parse Exception occured!");
			e.printStackTrace();
		}

		
		System.out.println("response: "+ responseObj.toJSONString());
		return responseObj;
		
	}
	
}
