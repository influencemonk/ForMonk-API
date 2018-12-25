package com.ForMonk2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstagramDataHelper;

@Controller
@RequestMapping("/instagram")
public class InstagramProfileController {
	
	InstagramDataHelper instagramDataHelper; 
	
	{
		instagramDataHelper = new InstagramDataHelper();
	}
	
	@RequestMapping(value="/profileSummary", method=RequestMethod.POST)
	public @ResponseBody String getProfileSummary(String username, Integer maxPosts) {
		return instagramDataHelper.getProfileSummary(username, maxPosts);
	}
	
	@RequestMapping(value="/currentAnalytics", method=RequestMethod.POST)
	public @ResponseBody String getCurrentAnalytics(String username) {
		return instagramDataHelper.getFullProfileAnalytics(username);
	}
	
	@RequestMapping(value="/predictedAnalytics", method=RequestMethod.POST)
	public @ResponseBody String getPredictedAnalytics(String username) {
		return null;
	}

}
