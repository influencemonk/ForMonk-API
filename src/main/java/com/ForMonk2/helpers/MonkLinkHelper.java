package com.ForMonk2.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.ForMonk2.model.ApiResponseModel;
import com.ForMonk2.model.FacebookAuthResponse;
import com.ForMonk2.model.FacebookIDResponse;
import com.ForMonk2.model.FacebookPagesResponse;
import com.ForMonk2.model.IMCModel;
import com.ForMonk2.model.InstagramBusinessAccountResponse;
import com.ForMonk2.model.InstagramMediaResponse;
import com.ForMonk2.constants.ApiConstants.RESPONSE;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;
import com.ForMonk2.utils.NetworkHandler;
import com.google.gson.Gson;



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
			params.put("fields", Constants.GRAPH_API.FIELD);
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
	 * @param clientId
	 * @param request
	 * @return
	 */
	public ApiResponseModel<AddLinkResponse> addLinkForUser(AddLinkRequest request){
		
		ApiResponseModel<AddLinkResponse> response = null;
		
		AddLinkResponse addLinkResponse = new AddLinkResponse();
		
		try {
		
			ProfileLinkDto profileLinkDto = request.getLinkData();
			
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
	
	
}
