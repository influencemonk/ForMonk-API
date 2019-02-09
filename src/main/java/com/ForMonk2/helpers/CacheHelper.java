package com.ForMonk2.helpers;


import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.ForMonk2.utils.GeneralUtils;

public class CacheHelper {


	public static void clearSingleCache(CacheManager cacheManager , String cacheName , String key) {
			
		Cache cache = cacheManager.getCache(cacheName);
		
		if(cache == null )
			return;
		
		if(GeneralUtils.stringIsNull(key))
			cacheManager.getCache(cacheName).clear();
		else
			cacheManager.getCache(cacheName).evict(key);
		
	}
	
	public static void clearAllCache(CacheManager cacheManager) {
		cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
	}
	
}
