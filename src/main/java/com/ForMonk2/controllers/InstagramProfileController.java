package com.ForMonk2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstagramDataHelper;
import com.ForMonk2.utils.Constants.INSTA_SCRAPER.ApiUser;

@Controller
@RequestMapping("/instagram")
public class InstagramProfileController {
	
	InstagramDataHelper instagramDataHelper; 
	
	{
		instagramDataHelper = new InstagramDataHelper();
	}
	
	@RequestMapping(value="/profileSummaryGQL", method=RequestMethod.GET)
	public @ResponseBody String getProfileSummaryGQL(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.imWeb);
	}
	
	@RequestMapping(value="/profileSummaryDI", method=RequestMethod.GET)
	public @ResponseBody String getProfileSummaryDI(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummaryGQL(username, maxPosts, ApiUser.diet);
	}
	
/*	@RequestMapping(value="/profileSummary", method=RequestMethod.GET)
	public @ResponseBody String getProfileSummary(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummary(username, maxPosts);
	}
	
	@RequestMapping(value="/currentAnalytics", method=RequestMethod.GET)
	public @ResponseBody String getCurrentAnalytics(String username) {
		return instagramDataHelper.getFullProfileAnalytics(username);
	}
*/	
	@RequestMapping(value="/currentAnalyticsGQL", method=RequestMethod.GET)
	public @ResponseBody String getCurrentAnalyticsGQL(String username) {
		return instagramDataHelper.getFullProfileAnalyticsGQL(username);
	}
		
	@RequestMapping(value="/predictedAnalytics", method=RequestMethod.GET)
	public @ResponseBody String getPredictedAnalytics(String username) {
		return null;
	}

}
