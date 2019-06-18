package com.ForMonk2.controllers;

import com.ForMonk2.model.*;
import com.ForMonk2.validators.annotations.ValidClientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ForMonk2.dao.IMCRepositoryDao;
import com.ForMonk2.dto.GetLinksResponse;
import com.ForMonk2.dto.UpdateLinkRequest;
import com.ForMonk2.dto.UpdateLinkResponse;
import com.ForMonk2.dto.AddLinkRequest;
import com.ForMonk2.dto.AddLinkResponse;
import com.ForMonk2.dto.DeleteLinkResponse;
import com.ForMonk2.helpers.MonkLinkHelper;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@Controller
@RequestMapping("/MonkLinks")
public class MonkLinkController {
	@Autowired
	private Environment env;
	@Autowired
	private IMCRepositoryDao imcManager;
	@Autowired
	private MonkLinkHelper monkLinkHelper;

	@RequestMapping(value = "v1/getOauthTokens" , method = RequestMethod.GET)
	ResponseEntity<?> getTokens(@RequestHeader(value = "ClientID") @ValidClientId String clientId , String authCode , String redirectUri) {

		FacebookAuthResponse facebookAuthResponse = MonkLinkHelper.getFacebookTokens(authCode, redirectUri , env);
		if(facebookAuthResponse == null )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(facebookAuthResponse , HttpStatus.OK);
	}

	@RequestMapping(value = "v1/getFacebookAccountId" , method = RequestMethod.GET)
	ResponseEntity<?> getFacebookAccountId(@RequestHeader("ClientID") @ValidClientId String clientId , String authToken) {

		FacebookIDResponse facebookIDResponse = MonkLinkHelper.getFacebookAccountId(authToken);
		
		if(facebookIDResponse == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(facebookIDResponse,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "v1/getFacebookPages" , method = RequestMethod.GET)
	ResponseEntity<?> getFacebookPages(@NotNull  String authToken , @NotNull String facebookUserId) {
		FacebookPagesResponse response = MonkLinkHelper.getFacebookPages(facebookUserId, authToken);
		
		return response == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(response , HttpStatus.OK);
	}

	@RequestMapping(value = "v1/getInstaBusinessAccounts" , method = RequestMethod.GET)
	ResponseEntity<?> getInstaBusinessAccounts(@NotNull  String authToken ,@NotNull String instaAccountId) {
		
		InstagramBusinessAccountResponse response = MonkLinkHelper.getInstaBusinessAccount(instaAccountId, authToken);
		
		if(response == null )
			return new ResponseEntity<>(Constants.INVALID_OBJECT , HttpStatus.OK);
		else {
			if(response.getInstagramBusinessAccount() == null )
				return new ResponseEntity<>("This user has no business account" , HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(response , HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "v1/getInstagramPosts" , method = RequestMethod.GET)
	ResponseEntity<?> getInstagramPosts(String authToken , String instaBusinessAccountId){
		
		InstagramMediaResponse response = MonkLinkHelper.getInstagramPosts(instaBusinessAccountId, authToken);
		
		return response == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : 
			new ResponseEntity<>(response , HttpStatus.OK);
		
	}

	@RequestMapping(value = "v2/getFacebookPages" , method = RequestMethod.GET)
	ResponseEntity<?> getFacebookPagesV2(String authCode , String redirectUri) {
		ApiResponseModel<FacebookPagesResponse> response = MonkLinkHelper.getFacebookPages(authCode, redirectUri, env);
		
		if(response == null ) {
			
			ApiResponseModel<Object> temp = new ApiResponseModel<Object>();
			temp.setError(true);
			temp.setMessage(Constants.INVALID_OBJECT);
			return new ResponseEntity<>(temp , HttpStatus.BAD_REQUEST);
			
		}else if(response.getError()) {
			
			return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
			
		}else {
			
			return new ResponseEntity<>(response , HttpStatus.OK);
			
		}
	}

	@RequestMapping(value = "v2/getInstagramPosts" , method = RequestMethod.GET)
	ResponseEntity<?> getInstagramPostsv2(String authToken , String facebookPageId) {
		
		ApiResponseModel<InstagramMediaResponse> instagramMediaResponse = MonkLinkHelper.getInstagramPostsV2(authToken, facebookPageId , imcManager);
		
		if(instagramMediaResponse == null )
			return GeneralUtils.throwGenericErrorResponse();
		
		return new ResponseEntity<>(instagramMediaResponse , instagramMediaResponse.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	}

	@RequestMapping(value = "v1/getInstagmUserInsights" , method = RequestMethod.GET)
	ResponseEntity<?> getInstagramInsights(@ValidClientId @RequestHeader(value = "ClientID") String clientId ,
										   @RequestParam @NotNull String authToken ,
										   @RequestParam @NotEmpty String instagramBusinessId)
	{
		return new ResponseEntity<>(MonkLinkHelper.getInstagramInsights(authToken , instagramBusinessId) , HttpStatus.OK);
	}

	@RequestMapping(value = "v1/getInstagramPostAnalytics" , method = RequestMethod.GET)
	ResponseEntity<?> getPostAnalytics(@RequestHeader @ValidClientId String ClientId ,
									   @RequestParam @NotEmpty  String authToken ,
									   @RequestParam @NotEmpty String postId)
	{
		InstagramPostsInsightsResponseModel response = MonkLinkHelper.getInstagramPostAnalytics(authToken , postId);
		return new ResponseEntity<>(response , response == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	}

	@RequestMapping(value = "v1/getInstagramAnalytics" , method = RequestMethod.GET)
	ResponseEntity<?> getInstagramAnalytics(@ValidClientId @RequestHeader(value = "ClientID") String clientId ,
											String authToken , String facebookPageId) {
		return new ResponseEntity<>(MonkLinkHelper.getInstagramAnalytics(facebookPageId , authToken , imcManager) , HttpStatus.OK);
	}

	@GetMapping(value = "v1/getLinks")
	ResponseEntity<?> getLinksForUser(@RequestHeader(value = "ClientID") String clientId, @RequestParam(name="imcId", required=true) String imcId) {
		
		ApiResponseModel<GetLinksResponse> getLinksResponse = monkLinkHelper.getLinksForUser(clientId, imcId);
		
		if(getLinksResponse == null )
			return GeneralUtils.throwGenericErrorResponse();
	
		return new ResponseEntity<>(getLinksResponse , getLinksResponse.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	
	}
	
	@PutMapping(value = "v1/addLink")
	ResponseEntity<?> addLinkForUser(@RequestBody AddLinkRequest request) {
		
		ApiResponseModel<AddLinkResponse> saveResponse = monkLinkHelper.addLinkForUser(request);
		
		if(saveResponse == null )
			return GeneralUtils.throwGenericErrorResponse();
	
		return new ResponseEntity<>(saveResponse , saveResponse.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	
	}

	@PostMapping(value = "v1/updateLink")
	ResponseEntity<?> updateLinkForPlId(@RequestBody UpdateLinkRequest request) {
		
		ApiResponseModel<UpdateLinkResponse> updateResponse = monkLinkHelper.updateLinkDetails(request);
		
		if(updateResponse == null )
			return GeneralUtils.throwGenericErrorResponse();
	
		return new ResponseEntity<>(updateResponse , updateResponse.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	
	}

	@RequestMapping(value = "v1/getTotalPostEngagement" , method = RequestMethod.GET)
	ResponseEntity<?> getTotalPostsEngagement(@ValidClientId @RequestHeader String clientId ,
											  @RequestParam @NotEmpty String instagramBusinessId ,
											  @RequestParam @NotEmpty String authToken) {
		return new ResponseEntity<>(MonkLinkHelper.getTotalPostsEngagement(authToken , instagramBusinessId) , HttpStatus.OK);
	}

	@RequestMapping(value = "v2/getTotalPostEngagement" , method = RequestMethod.GET)
	ResponseEntity<?> getTotalPostsEngagementV2(@ValidClientId @RequestHeader String clientId ,
											  @RequestParam @NotEmpty String instagramBusinessId ,
											  @RequestParam @NotEmpty String authToken) {
		return new ResponseEntity<>(MonkLinkHelper.getTotalPostsEngagementV2(authToken , instagramBusinessId) , HttpStatus.OK);
	}
	
	@DeleteMapping(value = "v1/deleteLink/{plId}")
	ResponseEntity<?> deleteLinkForPlId(@PathVariable("plId") String plId) {
		
		ApiResponseModel<DeleteLinkResponse> updateResponse = monkLinkHelper.deleteLinkForPlId(plId);
		
		if(updateResponse == null )
			return GeneralUtils.throwGenericErrorResponse();
	
		return new ResponseEntity<>(updateResponse , updateResponse.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	
	}
	
	
	
}
