package com.ForMonk2.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ForMonk2.helpers.CacheHelper;
import com.ForMonk2.utils.Constants;
import com.ForMonk2.utils.GeneralUtils;

@Component
public class CacheService {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Scheduled(fixedDelay = 12*60*60*1000)
	public void clearprofileSummaryCache() {
		CacheHelper.clearSingleCache(cacheManager, Constants.CacheConstants.PROFILE_SUMMARY, null);
		GeneralUtils.log(CacheService.class.getName(), Constants.CacheConstants.PROFILE_SUMMARY + " :: CACHE CLEARED");
	}
	
}
