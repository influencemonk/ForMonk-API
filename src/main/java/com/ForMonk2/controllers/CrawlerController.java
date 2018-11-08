package com.ForMonk2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstaCrawlerHelper;

@Controller
public class CrawlerController {

	@GetMapping("/getInstaData")
	@ResponseBody
	public String getInstaData(@RequestParam(name="username", required=true) String username) {		
		InstaCrawlerHelper crawlerHelper = new InstaCrawlerHelper();
		return crawlerHelper.getProfileData(username);		
	}
	
}
