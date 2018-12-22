package com.ForMonk2.controllers;


import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstaCrawlerHelper;

@Controller
public class CrawlerController {

	@GetMapping("/instaData")
	@ResponseBody
	public String getInstaData(@RequestParam(name="username", required=true) String username) {		
		InstaCrawlerHelper crawlerHelper = new InstaCrawlerHelper();
		return crawlerHelper.getProfileData(username);		
	}
	
	@GetMapping("/instantInstaData")
	@ResponseBody
	public JSONObject getInstantInstaData(@RequestParam(name="username", required=true) String username) {		
		InstaCrawlerHelper crawlerHelper = new InstaCrawlerHelper();
		return crawlerHelper.getInstantProfileData(username);		
	}
	
	@GetMapping("/postsData")
	@ResponseBody
	public String getInstantInstaDataXml(@RequestParam(name="username", required=true) String username) {		
		InstaCrawlerHelper crawlerHelper = new InstaCrawlerHelper();
		return crawlerHelper.getPostsData(username);
	}
}
