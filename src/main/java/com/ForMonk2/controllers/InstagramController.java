package com.ForMonk2.controllers;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.ForMonk2.helpers.InstagramHelper;
import com.ForMonk2.model.ApiResponsModel;
import com.ForMonk2.utils.NetworkHandler;

@Controller
public class InstagramController {
	
	
	@GetMapping("/instaLogin")
    @ResponseBody
    public RedirectView instaLogin() {
        
		String response = InstagramHelper.userLogin();
		
		if(response != null ) {
			return new RedirectView(response);
			
		}else {
			return new RedirectView("Null Object");
			
		}
		
    }
	
	@GetMapping("/instaCallbackUrl")
	@ResponseBody
	
	public String callBackurl(
			
			@RequestParam(name="code", 
			required=false, 
			defaultValue="NA")
			String code) {
		
		return InstagramHelper.getAccessToken(code);
		
	}
	
	@GetMapping("/ping")
	public boolean ping() {
		return true;
	}
	

}
