package com.ForMonk2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeneralUtils {

private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class);	
	
public static void printStackTrace(String message) {
		
		if(message != null ) {
			if(message.trim() != "") {
				System.out.println(message);
			}
		}
	}


public static void printTimeDifference(long startTime , String API) {

	long endTime = System.currentTimeMillis();
	
	long deltaTime = endTime - startTime;

	logger.info("Time taken by " + API +" : " + (deltaTime/100)+"s");
}
	
}
