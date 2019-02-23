package com.ForMonk2.helpers;

import java.io.IOException;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ForMonk2.collectionHelpers.IFTRepositoryManager;
import com.ForMonk2.model.FollowerTrendMasterModel;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.Constants.INSTA_SCRAPER.ApiUser;
import com.ForMonk2.utils.CrawlerDataParser;
import com.ForMonk2.utils.NetworkHandler;

@SuppressWarnings("unchecked")
public class InstagramDataHelper {

	/**
	 * Method to get Profile Summary of a given user from Instagram GraphQL API
	 * 
	 * @param imweb
	 * 
	 * @param       username: Instagram username of any user
	 * @param       maxPosts: Maximum number of posts to process
	 * @return User Instgaram profile summary
	 */
	public JSONObject getProfileSummaryGQL(String username, Integer maxPosts, ApiUser apiUser) {

		JSONObject responseObj = new JSONObject();

		JSONParser parser = new JSONParser();

		CrawlerDataParser crawlerParser = new CrawlerDataParser();

		NetworkHandler networkHandler = NetworkHandler.getInstance();

		if (maxPosts == null) {
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
				crawlerResposne = networkHandler.sendGet(Constants.INSTA_SCRAPER.getUserProfileURL(username), queries,
						headers);
			} catch (RuntimeException e) {
				e.printStackTrace();
				responseObj.put("type", "invalid_username");
				responseObj.put("message", "Username not found!");
				return responseObj;
			}

			if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {

				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);
				
				JSONObject profileData = crawlerParser.getPostSummaryInfo(crawlerResponseObj, maxPosts,
											Constants.INSTA_SCRAPER.DataSource.graphql);
				
				// Get IMC ID from DB:
				if(apiUser == ApiUser.imWeb) {
					
					IMCDataHelper imcDataHelper = new IMCDataHelper();
					String imcId = imcDataHelper.getIMCObjectId(Constants.SOCIAL_CLIENTS.INSTAGRAM, username);
					
					profileData.put("imc_id", imcId);
					
				}
				
				// Parse the received profile data:
				responseObj.put("data", profileData);
				responseObj.put("result", "true");
			} else {
				responseObj.put("result", "false");
				responseObj.put("type", "no_data");
				responseObj.put("message", "Failed to receive data!");
			}

		} catch (IOException e) {
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

		if (responseObj.get("result").equals("true")) {
			
			if(apiUser != ApiUser.imWeb) {
				DietProfileHelper dietProfileManager = new DietProfileHelper();
				DBCollections collection = DBCollections.MonkDB;
				if (apiUser == ApiUser.diet) {
					collection = DBCollections.DietDB;
				} else if (apiUser == ApiUser.getics) {
					collection = DBCollections.GeticsDB;
				}
	
				if (!dietProfileManager.isAlreadyAdded(username, collection)) {
					dietProfileManager.addToDB(username, collection);
				}
			}

		}

		
		return responseObj;

	}  
	/**
	 * Method to get Profile Summary of a given user from InfluenceMonk Instagram
	 * Scraper
	 * 
	 * @param username: Instagram username of any user
	 * @param maxPosts: Maximum number of posts to process
	 * @return User Instgaram profile summary
	 */
	public String getProfileSummary(String username, Integer maxPosts) {

		JSONObject responseObj = new JSONObject();

		final String INSTA_SCRAPER = "http://influencemonk.com/insta-data/parser.php";
		final int MAX_RETRY = 5;

		JSONParser parser = new JSONParser();

		CrawlerDataParser crawlerParser = new CrawlerDataParser();

		NetworkHandler networkHandler = NetworkHandler.getInstance();

		if (maxPosts == null) {
			maxPosts = 12;
		}

		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());

		try {

			responseObj.put("result", "false");

			String crawlerResposne = "";
			try {
				for (int i = 0; i < MAX_RETRY; i++) {

					crawlerResposne = networkHandler.sendGet(INSTA_SCRAPER, queries, null);

					if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
						break;
					}
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				responseObj.put("type", "invalid_username");
				responseObj.put("message", "Username not found!");
				return responseObj.toJSONString();
			}

			if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {

				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);

				// Parse the received profile data:
				responseObj.put("data", crawlerParser.getPostSummaryInfo(crawlerResponseObj, maxPosts,
						Constants.INSTA_SCRAPER.DataSource.im_parser));
				responseObj.put("result", "true");
			} else {
				responseObj.put("result", "false");
				responseObj.put("type", "no_data");
				responseObj.put("message", "Failed to receive data!");
			}

		} catch (IOException e) {
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

		System.out.println("response: " + responseObj.toJSONString());
		return responseObj.toJSONString();

	}

	/**
	 * Method to get All Data for profile analytics of a given user from Instagram
	 * GraphQL API
	 */
	public JSONObject getFullProfileAnalyticsGQL(String username) {

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
				crawlerResposne = networkHandler.sendGet(Constants.INSTA_SCRAPER.getUserProfileURL(username), queries,
						headers);
			} catch (RuntimeException e) {
				e.printStackTrace();

				responseObj.put("type", "invalid_username");
				responseObj.put("message", "Username not found!");
				return responseObj;
			}

			if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {

				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);

				// Parse the received profile data:
				profileInfoObj = crawlerParser.getProfileInfoGraphQl(crawlerResponseObj);

				String instagramId = (String) profileInfoObj.get("id");
				boolean isPrivateProfile = (boolean) profileInfoObj.get("is_private");

				long totalLikes = -1;
				long totalComments = -1;

				if (!isPrivateProfile) {

					boolean postHasNextPage = false;
					String postEndCursor = null;

					JSONArray mainPostsArray = new JSONArray();

					do {
						String postsVariables = Constants.INSTA_SCRAPER.getPostsJsonVariables(instagramId, 50,
								postEndCursor);

						Map<String, String> postDataQueries = new HashMap<String, String>();
						postDataQueries.put("query_hash", Constants.INSTA_SCRAPER.QUERY_HASH_POSTS);
						postDataQueries.put("variables", postsVariables);

						Map<String, String> postDataHeaders = new HashMap<String, String>();
						postDataHeaders.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());

						System.out.println("POST COOKIE: " + Constants.INSTA_SCRAPER.getCookieVar2());
						String postsResponse = "";
						try {
							// Get all posts data of the user:
							postsResponse = networkHandler.sendGet(Constants.INSTA_SCRAPER.BASE_URL, postDataQueries,
									postDataHeaders);
						} catch (SocketException e) {
							e.printStackTrace();
							postHasNextPage = true;
							continue;
						}

						if (!postsResponse.equals("") || null != postsResponse) {

							if (responseObj.get("result").equals("false")) {
								responseObj.put("result", "true");
							}

							if (totalLikes == -1 || totalComments == -1) {
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

					} while (postHasNextPage);

					profileInfoObj.put("posts_data", mainPostsArray);

				} else {
					profileInfoObj.put("posts_data", null);
				}

				profileInfoObj.put("total_likes", totalLikes);
				profileInfoObj.put("total_comments", totalComments);

				System.out.println("PROFILE DATA: " + profileInfoObj.toJSONString());

			} else {
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

		//response = responseObj.toJSONString();

		return responseObj;
	}

	/**
	 * Method to get All Data for profile analytics of a given user from Instagram
	 * GraphQL API
	 */
	public JSONObject getFullProfileAnalytics(String username) {

		JSONObject responseObj = new JSONObject();

		final String INSTA_SCRAPER = "http://influencemonk.com/insta-data/parser.php";
		final int MAX_RETRY = 5;

		Map<String, String> queries = new HashMap<String, String>();
		queries.put("insta_id", username);

		JSONObject profileInfoObj = new JSONObject();

		JSONParser parser = new JSONParser();

		CrawlerDataParser crawlerParser = new CrawlerDataParser();

		NetworkHandler networkHandler = NetworkHandler.getInstance();

		try {
			responseObj.put("result", "false");

			String crawlerResposne = "";
			try {
				for (int i = 0; i < MAX_RETRY; i++) {

					crawlerResposne = networkHandler.sendGet(INSTA_SCRAPER, queries, null);

					if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {
						break;
					}
				}
			} catch (RuntimeException e) {
				e.printStackTrace();

				responseObj.put("type", "invalid_username");
				responseObj.put("message", "Username not found!");
				return responseObj;
			}

			if (!crawlerResposne.isEmpty() && null != crawlerResposne && !crawlerResposne.equals("")) {

				JSONObject crawlerResponseObj = (JSONObject) parser.parse(crawlerResposne);

				// Parse the received profile data:
				profileInfoObj = crawlerParser.getProfileInfo(crawlerResponseObj);

				String instagramId = (String) profileInfoObj.get("id");
				boolean isPrivateProfile = (boolean) profileInfoObj.get("is_private");

				long totalLikes = -1;
				long totalComments = -1;

				if (!isPrivateProfile) {

					boolean postHasNextPage = false;
					String postEndCursor = null;

					JSONArray mainPostsArray = new JSONArray();

					TreeMap<Long, JSONObject> mainPostsMap = new TreeMap<Long, JSONObject>(Collections.reverseOrder());

					do {
						String postsVariables = Constants.INSTA_SCRAPER.getPostsJsonVariables(instagramId, 50,
								postEndCursor);

						Map<String, String> postDataQueries = new HashMap<String, String>();
						postDataQueries.put("query_hash", Constants.INSTA_SCRAPER.QUERY_HASH_POSTS);
						postDataQueries.put("variables", postsVariables);

						Map<String, String> postDataHeaders = new HashMap<String, String>();
						postDataHeaders.put("cookie", Constants.INSTA_SCRAPER.getCookieVar2());

						System.out.println("POST COOKIE: " + Constants.INSTA_SCRAPER.getCookieVar2());
						String postsResponse = "";
						try {
							// Get all posts data of the user:
							postsResponse = networkHandler.sendGet(Constants.INSTA_SCRAPER.BASE_URL, postDataQueries,
									postDataHeaders);
						} catch (SocketException e) {
							e.printStackTrace();
							postHasNextPage = true;
							continue;
						}

						if (!postsResponse.equals("") || null != postsResponse) {

							if (responseObj.get("result").equals("false")) {
								responseObj.put("result", "true");
							}

							if (totalLikes == -1 || totalComments == -1) {
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

							TreeMap<Long, JSONObject> pagePostsMap = (TreeMap<Long, JSONObject>) postsInfoPage
									.get("page_posts_map");

							mainPostsMap.putAll(pagePostsMap);

							mainPostsArray.addAll(postsArray);
						}

					} while (postHasNextPage);

					profileInfoObj.put("posts_data", mainPostsArray);

					JSONArray topPosts = new JSONArray();

					int count = 0;
					for (Long key : mainPostsMap.keySet()) {
						if (count >= 5) {
							break;
						}
						topPosts.add(mainPostsMap.get(key));
						count++;
					}

					profileInfoObj.put("top_posts", topPosts);

				} else {
					profileInfoObj.put("posts_data", null);
				}

				profileInfoObj.put("total_likes", totalLikes);
				profileInfoObj.put("total_comments", totalComments);

				System.out.println("PROFILE DATA: " + profileInfoObj.toJSONString());

			} else {
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

		// response = responseObj.toJSONString();

		return responseObj;
	}
	
	public static FollowerTrendMasterModel getProfileTrend(String clientId , String imcId , IFTRepositoryManager iftRepositoryManager) {
		try {
			
			FollowerTrendMasterModel followerTrendMasterModel =  iftRepositoryManager.findByimcId(imcId);
			
			if(followerTrendMasterModel == null) {
				followerTrendMasterModel = new FollowerTrendMasterModel(); // data doesnt exists
			}
			
			return followerTrendMasterModel;
			
		}catch(Exception e ) {
			return null;
		}
	}

}
