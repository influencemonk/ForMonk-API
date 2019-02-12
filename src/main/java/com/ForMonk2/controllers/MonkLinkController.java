package com.ForMonk2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ForMonk2.helpers.MonkLinkHelper;
import com.ForMonk2.model.FacebookAuthResponse;
import com.ForMonk2.model.FacebookIDResponse;
import com.ForMonk2.model.FacebookPagesResponse;
import com.ForMonk2.model.InstagramBusinessAccountResponse;
import com.ForMonk2.model.InstagramMediaResponse;
import com.ForMonk2.utils.Constants;

@Controller
@RequestMapping("/MonkLinks")
public class MonkLinkController {

	@Autowired
	private Environment env;
	
	@RequestMapping(value = "getOauthTokens" , method = RequestMethod.GET)
	ResponseEntity<?> getTokens(@RequestHeader(value = "ClientID") String clientId , String authCode) {
		
		
		if(! Constants.SOCIAL_CLIENTS.clientIds.contains(clientId)) {
			return new ResponseEntity<>(Constants.ResponseMessages.INVALID_CLIENT_ID, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		FacebookAuthResponse facebookAuthResponse = MonkLinkHelper.getFacebookTokens(authCode , env);
		
		if(facebookAuthResponse == null )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(facebookAuthResponse , HttpStatus.OK);
	}
	
	@RequestMapping(value = "getFacebookAccountId" , method = RequestMethod.GET)
	ResponseEntity<?> getFacebookAccountId(@RequestHeader("ClientID") String clientId , String authToken) {
		if(! Constants.SOCIAL_CLIENTS.clientIds.contains(clientId)) {
			return new ResponseEntity<>(Constants.ResponseMessages.INVALID_CLIENT_ID, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	
		FacebookIDResponse facebookIDResponse = MonkLinkHelper.getFacebookAccountId(authToken);
		
		if(facebookIDResponse == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(facebookIDResponse,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "getFacebookPages" , method = RequestMethod.GET)
	ResponseEntity<?> getFacebookPages(String authToken , String facebookUserId) {
		FacebookPagesResponse response = MonkLinkHelper.getFacebookPages(facebookUserId, authToken);
		
		return response == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(response , HttpStatus.OK);
	}
	
	@RequestMapping(value = "getInstaBusinessAccounts" , method = RequestMethod.GET)
	ResponseEntity<?> getInstaBusinessAccounts(String authToken , String instaAccountId) {
		
		InstagramBusinessAccountResponse response = MonkLinkHelper.getInstaBusinessAccount(instaAccountId, authToken);
		
		if(response == null )
			return new ResponseEntity<>(Constants.INVALID_OBJECT , HttpStatus.OK);
		else {
			if(response.getInstagramBusinessAccount() == null )
				return new ResponseEntity<>("This user has no business account" , HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(response , HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "getInstagramPosts" , method = RequestMethod.GET)
	ResponseEntity<?> getInstagramPosts(String authToken , String instaBusinessAccountId){
		
		InstagramMediaResponse response = MonkLinkHelper.getInstagramPosts(instaBusinessAccountId, authToken);
		
		return response == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : 
			new ResponseEntity<>(response , HttpStatus.OK);

		
	}
	
	
}
