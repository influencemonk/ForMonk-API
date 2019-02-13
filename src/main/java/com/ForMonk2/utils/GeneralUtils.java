package com.ForMonk2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ForMonk2.model.ApiResponseModel;


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


public static boolean stringIsNull(String s) {
	if(s == null )
		return true;
	else
		return s.trim().length() == 0;
}

public static void log(String tag , String message) {
	
	if(stringIsNull(message) || stringIsNull(tag))
		return ;
	
	System.out.println(String.format("%s:: 	%s", tag , message));
}

public static ResponseEntity<?> throwGenericErrorResponse() {
	
	ApiResponseModel<Object> responseModel = new ApiResponseModel<>();
	responseModel.setError(true);
	responseModel.setMessage(Constants.INVALID_OBJECT);
	
	return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	
}

}
