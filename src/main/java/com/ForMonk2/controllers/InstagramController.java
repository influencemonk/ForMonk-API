package com.ForMonk2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.InstagramHelper;

@Controller
public class InstagramController {
	
	
	@GetMapping("/instaLogin")
    @ResponseBody
    public Object instaLogin() {
        
		return InstagramHelper.userLogin();
		
    }
	
	@GetMapping("/callBackurl")
	public Object callBackurl(String authCode) {
		
		return authCode;
		
	}
	

}
