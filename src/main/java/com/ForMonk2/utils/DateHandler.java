package com.ForMonk2.utils;

import java.time.Instant;

public class DateHandler {

	public static long getCurrentTimeStamp() {
		Instant instant = Instant.now();
		return instant.getEpochSecond();
	}
	
}
