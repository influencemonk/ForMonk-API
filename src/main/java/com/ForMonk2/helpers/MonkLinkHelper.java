package com.ForMonk2.helpers;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ForMonk2.model.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ForMonk2.dao.IMCRepositoryDao;
import com.ForMonk2.dao.PLRepositoryDao;
import com.ForMonk2.dto.GetLinksResponse;
import com.ForMonk2.dto.ProfileLinkDto;
import com.ForMonk2.dto.UpdateLinkRequest;
import com.ForMonk2.dto.UpdateLinkResponse;
import com.ForMonk2.dto.AddLinkRequest;
import com.ForMonk2.dto.AddLinkResponse;
import com.ForMonk2.dto.DeleteLinkResponse;
import com.ForMonk2.entity.ProfileLink;
import com.ForMonk2.constants.ApiConstants.RESPONSE;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;
import org.springframework.util.Assert;


@Component
public class MonkLinkHelper {
	

	@Autowired
	private PLRepositoryDao plRepositoryDao;
	
	public static FacebookAuthResponse getFacebookTokens(String code , String redirectUri, Environment env) {
		try {

			Map<String, String> params = new HashMap<String, String>();
			params.put("client_id", env.getProperty("FACEBOOK.APP_ID"));
			params.put("client_secret", env.getProperty("FACEBOOK.SECRET"));
			params.put("redirect_uri", 	redirectUri);
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
	
	public static InstagramBusinessAccountResponse getInstaBusinessAccount(String facebookPageId , String accessToken) {
		
		try {
			
			String baseUrl = NetworkHandler.getInstance().formatBaseUrl(Constants.GRAPH_API.ENDPOINT, facebookPageId);
			
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
			params.put("fields", Constants.GRAPH_API.POSTS_FIELD);
			params.put("access_token" , authToken);
			
			String response = NetworkHandler.getInstance().sendGet(baseUrl, params);
			
			return new Gson().fromJson(response, InstagramMediaResponse.class);
			
		}catch(Exception e ) {
			return null;
		}
		
	}
	
	public static ApiResponseModel<FacebookPagesResponse> getFacebookPages(String authCode, String redirectUri , Environment env) {
		
		try {
			ApiResponseModel<FacebookPagesResponse> mainResponse = new ApiResponseModel<FacebookPagesResponse>();
			
			FacebookAuthResponse authData = getFacebookTokens(authCode, redirectUri , env);
			
			if(authData == null)
			{
				mainResponse.setError(true);
				mainResponse.setMessage("Unable to get auth tokens");
				return mainResponse;
			}
			
			FacebookIDResponse facebookIdResponse = getFacebookAccountId(authData.getAccessToken());
			
			if(facebookIdResponse == null ) {
				mainResponse.setError(true);
				mainResponse.setMessage("Unable to get user facebook ID");
				return mainResponse;
			}
			
			FacebookPagesResponse facebookPages = getFacebookPages(facebookIdResponse.getId() , authData.getAccessToken());
			
			if(facebookPages == null )
			{
				mainResponse.setError(true);
				mainResponse.setMessage("The user has no facebook page");
				return mainResponse;
			}
			
			mainResponse.setServerObject(facebookPages);
			mainResponse.setError(false);
			
			return mainResponse;
		}catch(Exception e ) {
			return null;
		}
		
	}

	public static JSONObject getInstagramInsights(String authToken, String instaBusinessAccountId){
		try {

			String baseUrl = NetworkHandler.getInstance().formatBaseUrl(
					Constants.GRAPH_API.ENDPOINT,
					instaBusinessAccountId ,
					"insights");

			Map<String , String> params = new HashMap<String , String>();
			params.put("metric", Constants.GRAPH_API.USER_INSIGHTS_FIELD);
			params.put("period" , "lifetime");
			params.put("access_token" , authToken);

			String response = NetworkHandler.getInstance().sendGet(baseUrl, params);

			return new Gson().fromJson(response , JSONObject.class);

		}catch(Exception e ) {
			return null;
		}
	}
	
	public static ApiResponseModel<InstagramMediaResponse> getInstagramPostsV2(String authToken , String facebookPageId, IMCRepositoryDao imcManager) {
		
		ApiResponseModel<InstagramMediaResponse> response = new ApiResponseModel<InstagramMediaResponse>();
		InstagramBusinessAccountResponse businessAccount = getInstaBusinessAccount(facebookPageId , authToken);
		
		if(businessAccount == null ) {
			response.setError(true);
			response.setMessage("This page has no business account associated with it");
			return response;
		}
		InstagramMediaResponse mediaResponse = getInstagramPosts(businessAccount.getInstagramBusinessAccount().getId() , authToken);
		
		if(mediaResponse == null ) {
			response.setError(true);
			response.setMessage("Unable to get posts from business account");
			return response;
		}
		
		
		IMCModel imcModel = imcManager.findBysocialAccounts(mediaResponse.getName(), Constants.INSTAGRAM_CONSTANTS.CLIENT_ID);
		
		if(imcModel == null ) {
			imcModel = imcManager.insert(mediaResponse.getName(), Constants.INSTAGRAM_CONSTANTS.CLIENT_ID);
		}
		
		if(imcModel != null ) {
			mediaResponse.setIMCId(imcModel.getId());
		}
				
		response.setError(false);
		response.setServerObject(mediaResponse);
		
		return response;
		
	}
	
	public static ApiResponseModel<InstagramAnalyticsModel> getInstagramAnalytics(String facebookPageId , String authToken , IMCRepositoryDao imcRepositoryDao)
	{
		ApiResponseModel<InstagramAnalyticsModel> response = new ApiResponseModel<>();
		final InstagramAnalyticsModel core = new InstagramAnalyticsModel();
		ApiResponseModel<InstagramMediaResponse> postsResponse = getInstagramPostsV2(authToken , facebookPageId , imcRepositoryDao);
		if(postsResponse.getError()) {
			response.setError(postsResponse.getError());
			response.setMessage(postsResponse.getMessage());
			return response;
		}

		InstagramMediaResponse instagramMediaResponse = postsResponse.getServerObject();
		Assert.notNull(instagramMediaResponse , "No instagram data available");

		core.setImcId(instagramMediaResponse.getImcId());
		core.setFollowersCount(instagramMediaResponse.getFollowers_count());
		core.setFollowsCount(instagramMediaResponse.getFollows_count());
		core.setNumberOfPosts(instagramMediaResponse.getMedia().getData().size());

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		tasks.add(CompletableFuture.runAsync(() -> {
			core.setAverageComments(getAverageComments(instagramMediaResponse));
		}, executorService));

		tasks.add(CompletableFuture.runAsync(() -> {
			core.setAverageLikes(MonkLinkHelper.getAverageLikes(instagramMediaResponse));
		}, executorService));

		try {
			CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).get();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally {
			response.setServerObject(core);
			return response;
		}
	}

	/**
	 * Method to get list of all links saved by a given user
	 * in ProfileLink collection
	 * 
	 * @param clientId
	 * @param imcId
	 * @return
	 */
	public ApiResponseModel<GetLinksResponse> getLinksForUser(String clientId, String imcId){
		
		ApiResponseModel<GetLinksResponse> response = null;
		
		try {
			
			List<ProfileLink> postLinkList = plRepositoryDao.findByImcId(imcId);
			GetLinksResponse getLinksResponse = new GetLinksResponse();
			getLinksResponse.setProfileLinks(postLinkList);
			getLinksResponse.setCount(postLinkList.size());
	
			response = new ApiResponseModel<>();
			response.setServerObject(getLinksResponse);
			response.setError(false);
			response.setMessage(RESPONSE.SUCCESS);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	/**
	 * Method to save link data for a given user in ProfileLink collection
	 *
	 * @param request
	 * @return
	 */
	public ApiResponseModel<AddLinkResponse> addLinkForUser(AddLinkRequest request){
		
		ApiResponseModel<AddLinkResponse> response = null;
		
		AddLinkResponse addLinkResponse = new AddLinkResponse();
		
		try {
		
			ProfileLinkDto profileLinkDto = request;
			
			ProfileLink profileLink = new ProfileLink();
			profileLink.setClientId(profileLinkDto.getClientId());
			profileLink.setPostImageUrl(profileLinkDto.getPostImageUrl());
			profileLink.setImcId(profileLinkDto.getImcId());
			profileLink.setLinkTitle(profileLinkDto.getLinkTitle());
			profileLink.setLinkUrl(profileLinkDto.getLinkUrl());
					
			if(profileLink.getImcId() == null || profileLink.getImcId().equals("")) {
				return null;
			}
			
			String currentTimestamp = GeneralUtils.getFormattedTimestamp();
			
			profileLink.setCreateOn(currentTimestamp);
			profileLink.setUpdateOn(currentTimestamp);
			
			profileLink = plRepositoryDao.insert(profileLink);
			
			if(profileLink.getId() != null && !profileLink.getId().equals("")) {
				
				addLinkResponse.setPlId(profileLink.getId());
				
				response = new ApiResponseModel<>();
				
				response.setServerObject(addLinkResponse);
				
				response.setError(false);
				response.setMessage(RESPONSE.SUCCESS);
				
			}
			
		}
		catch(Exception e) {
			response = null;
			e.printStackTrace();
			
		}
		
		return response;
		
	}
	
	/**
	 * Method to update profile link details for a given plId
	 * in ProfileLink collection
	 * 
	 * @param request
	 * @return
	 */
	public ApiResponseModel<UpdateLinkResponse> updateLinkDetails(UpdateLinkRequest request){
		
		ApiResponseModel<UpdateLinkResponse> response = null; 
		
		try {
			
			String plId = request.getPlId();
			String linkTitle = request.getLinkTitle();
			String linkUrl = request.getLinkUrl();
			String postImageUrl = request.getPostImageUrl();
			
			Optional<ProfileLink> plSearchResult = plRepositoryDao.findById(plId);
			
			response = new ApiResponseModel<>();
			
			if(plSearchResult.isPresent()) {
				
				ProfileLink profileLink = plSearchResult.get();
				
				if(linkTitle != null && !linkTitle.equals("")) {
					profileLink.setLinkTitle(linkTitle);
				}
				
				if(linkUrl != null && !linkUrl.equals("")) {
					profileLink.setLinkUrl(linkUrl);
				}
				
				if(postImageUrl != null && !postImageUrl.equals("")) {
					profileLink.setPostImageUrl(postImageUrl);
				}
				
				String currentTimestamp = GeneralUtils.getFormattedTimestamp();
				
				profileLink.setUpdateOn(currentTimestamp);
				
				profileLink = plRepositoryDao.save(profileLink);
				
				UpdateLinkResponse updateLinkResponse = new UpdateLinkResponse();
				updateLinkResponse.setPlId(profileLink.getId());
				
				response.setServerObject(updateLinkResponse);
				response.setError(false);
				response.setMessage(RESPONSE.SUCCESS);
				
			}
			else {
				response.setError(true);
				response.setMessage(RESPONSE.FAILED);
			}
			
		}
		catch(Exception e) {
			response = null;
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	/**
	 * Method to delete the document in ProfileLink collection with provided plId
	 *  
	 * @param plId
	 * @return
	 */
	public ApiResponseModel<DeleteLinkResponse> deleteLinkForPlId(String plId){
		
		ApiResponseModel<DeleteLinkResponse> response = null;
		
		try {
			
			plRepositoryDao.deleteById(plId);
			
			response = new ApiResponseModel<>();
			
			response.setError(false);
			response.setMessage(RESPONSE.SUCCESS);
			
		}
		catch(Exception e) {
			
			response = null;
			e.printStackTrace();
			
		}
		
		return response;
		
	}

	private static double getAverageLikes(InstagramMediaResponse instagramMediaResponse) {
		return instagramMediaResponse
				.getMedia()
				.getData()
				.stream()
				.map(x -> x.getLikeCount())
				.reduce(0 , (a, b)-> a + b);

	}

	private static double getAverageComments(InstagramMediaResponse instagramMediaResponse) {
		return instagramMediaResponse
				.getMedia()
				.getData()
				.stream()
				.map(x -> x.getCommentsCount())
				.reduce(0 , (a, b)-> a + b);

	}

	public static InstagramPostsInsightsResponseModel getInstagramPostAnalytics(String authToken , String postId) {
		String baseUrl = NetworkHandler.getInstance().formatBaseUrl(Constants.GRAPH_API.ENDPOINT , postId , "insights");
		HashMap<String , String> params = new HashMap<>();
		params.put("access_token" , authToken);
		params.put("metric" , "engagement");
		try {
			String response = NetworkHandler.getInstance().sendGet(baseUrl , params);
			return new Gson().fromJson(response , InstagramPostsInsightsResponseModel.class);
		} catch (IOException e) {
			return null;
		}
	}

	public static double getTotalPostsEngagement(String authToken , String instagramBussinessId){
		InstagramMediaResponse instagramMediaResponse = getInstagramPosts(instagramBussinessId , authToken);
		Assert.notNull(instagramBussinessId , "Unable to get any posts");

		System.out.println(Instant.now() + "Getting posts engagement ");

		double totalEngagement = instagramMediaResponse
				  .getMedia()
				  .getData()
				  .parallelStream()
				  .map(x -> getEngagementFromPostAnalytics(getInstagramPostAnalytics(authToken , x.getId())))
				  .reduce(0.0 , (a,b) -> a + b);

		System.out.println(Instant.now() + "Got posts engagement ");

		return totalEngagement;
	}

	public static double getTotalPostsEngagementV2(String authToken , String instagramBussinessId) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		List<InstagramPostsInsightsResponseModel> postsData = new ArrayList<>();
		Queue<CompletableFuture<Void>> futures = new LinkedList<>();
		InstagramMediaResponse instagramMediaResponse = getInstagramPosts(instagramBussinessId , authToken);

		System.out.println(instagramMediaResponse.getMedia().getData().size() + " posts found ");

		instagramMediaResponse.getMedia().getData().forEach(x -> {
			futures.add(CompletableFuture.runAsync(() -> postsData.add(getInstagramPostAnalytics(authToken , x.getId())),executorService));
		});
		try {
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
			Double engagement = postsData.parallelStream().map(x -> getEngagementFromPostAnalytics(x))
					.reduce(0.0,(a, b) -> a + b);
			executorService.shutdownNow();
			return engagement;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return -1;
	}

	private static double getEngagementFromPostAnalytics(InstagramPostsInsightsResponseModel model) {
		try{
			return model.getData().get(0).getValues().get(0).getValue();
		}catch (Exception e){
			return -10000;
		}
	}
}
