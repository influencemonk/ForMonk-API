package com.ForMonk2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ForMonk2.helpers.CacheHelper;
import com.ForMonk2.utils.Constants;

@Controller
@RequestMapping("/Cache")
public class CacheController {


	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(value = "/clearAllCache" , method = RequestMethod.GET) 
	public ResponseEntity<?> clearAllCache(@RequestHeader(value = "ClientID") String clientId){
		
		try {
			
			if(! Constants.SOCIAL_CLIENTS.clientIds.contains(clientId)) {
				return new ResponseEntity<>(Constants.ResponseMessages.INVALID_CLIENT_ID, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			//CacheHelper.clearAllCache(cacheManager);
			
			cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}catch(Exception e ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/clearSingleCache" , method = RequestMethod.GET)
	public ResponseEntity<?> clearSingleCache(@RequestParam(value = "value") String cacheName , @RequestParam(value = "key", required = false) String key) {
		try {
			
			CacheHelper.clearSingleCache(cacheManager,cacheName ,  key);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}catch(Exception e ) {
			
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
	}
	
}
