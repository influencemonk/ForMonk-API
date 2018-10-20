package com.ForMonk2.utils;

public class GeneralUtils {

public static void printStackTrace(String message) {
		
		if(message != null ) {
			if(message.trim() != "") {
				System.out.println(message);
			}
		}
	}
	
}
