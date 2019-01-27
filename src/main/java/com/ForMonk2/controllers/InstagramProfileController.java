package com.ForMonk2.controllers;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstagramDataHelper;
import com.ForMonk2.model.FollowerTrendMasterModel;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.Constants.INSTA_SCRAPER.ApiUser;

@Controller
@RequestMapping("/instagram")
public class InstagramProfileController {

	InstagramDataHelper instagramDataHelper;

	{
		instagramDataHelper = new InstagramDataHelper();
	}

	@RequestMapping(value = "/profileSummaryGQL", method = RequestMethod.GET)
	public @ResponseBody JSONObject getProfileSummaryGQL(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.gSheet);
	}

	@RequestMapping(value = "/profileSummaryDI", method = RequestMethod.GET)
	public @ResponseBody JSONObject getProfileSummaryDI(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.diet);
	}

	@RequestMapping(value = "/profileSummaryGE", method = RequestMethod.GET)
	public @ResponseBody JSONObject getProfileSummaryGE(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.getics);
	}

	@RequestMapping(value = "/currentAnalyticsGQL", method = RequestMethod.GET)
	public @ResponseBody JSONObject getCurrentAnalyticsGQL(String username) {
		return instagramDataHelper.getFullProfileAnalytics(username);
	}

	@RequestMapping(value = "/profileTrend/{imcId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProfileTrend(@RequestHeader(value = "ClientID") String clientId,
			@PathVariable("imcId") String imcId) {
		try {

			if(! Constants.SOCIAL_CLIENTS.clientIds.contains(clientId)) {
				return new ResponseEntity<>(Constants.ResponseMessages.INVALID_CLIENT_ID, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			FollowerTrendMasterModel followerTrendMaster = InstagramDataHelper.getProfileTrend(clientId, imcId);

			if (followerTrendMaster.getData() != null && followerTrendMaster.getData().size() > 0) {
				return new ResponseEntity<>(followerTrendMaster, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(followerTrendMaster, HttpStatus.NO_CONTENT);

			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/profileSummary", method = RequestMethod.GET)
	public ResponseEntity<?> getProfileSummary(@RequestHeader(value = "ClientID") String clientId, String username, 
												Integer maxPosts) {	
		try {

			if(! Constants.SOCIAL_CLIENTS.clientIds.contains(clientId)) {
				return new ResponseEntity<>(Constants.ResponseMessages.INVALID_CLIENT_ID, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			return new ResponseEntity<>(instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.imWeb), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
