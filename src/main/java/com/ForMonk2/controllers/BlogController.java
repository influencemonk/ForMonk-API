package com.ForMonk2.controllers;

import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ForMonk2.helpers.BlogHelper;
import com.ForMonk2.model.BlogModels.SendEmailRequest;
import com.ForMonk2.utils.Constants;

import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.*;

@Controller
@RefreshScope
@RequestMapping("/Blog")
public class BlogController {

	@Value("${mongo.username: asd}")
	private String message;

	@CrossOrigin(origins = Constants.MAIN_ENDPOINT)
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public @ResponseBody
	Object sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {

		try {
			Object response = BlogHelper.sendEmail(sendEmailRequest);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkScheduling", method = RequestMethod.POST)
	public ResponseEntity<?> checkScheduling(@RequestBody SendEmailRequest sendEmailRequest, long delay) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		scheduledExecutorService.schedule(() -> {
			sendEmail(sendEmailRequest);
		}, delay, TimeUnit.MILLISECONDS);
		return new ResponseEntity("password", HttpStatus.OK);
	}

	@RequestMapping(value = "/config-server", method = RequestMethod.GET)
	public ResponseEntity<?> asd() {
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody
	boolean ping() {
		return true;
	}

}