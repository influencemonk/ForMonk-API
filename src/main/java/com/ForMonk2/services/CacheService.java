package com.ForMonk2.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ForMonk2.helpers.CacheHelper;
import com.ForMonk2.utils.Constants;

@Component
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	@Autowired
	private CacheManager cacheManager;
	
	@Scheduled(fixedDelay = 12*60*60*1000)
	public void clearprofileSummaryCache() {
		CacheHelper.clearSingleCache(cacheManager, Constants.CacheConstants.PROFILE_SUMMARY, null);
		logger.info(Constants.CacheConstants.PROFILE_SUMMARY + " CACHE cleared");
	}
	
}
