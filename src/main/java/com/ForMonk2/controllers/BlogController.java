package com.ForMonk2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ForMonk2.helpers.BlogHelper;
import com.ForMonk2.model.BlogModels.SendEmailRequest;

@Controller
@RequestMapping("/Blog")
public class BlogController {

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
   
    public @ResponseBody Object sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
		
		try {
			Object response = BlogHelper.sendEmail(sendEmailRequest);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
    }
	
	@RequestMapping(value = "/ping" , method = RequestMethod.GET)
	public @ResponseBody boolean ping() {
		return true;
	}
	
}
