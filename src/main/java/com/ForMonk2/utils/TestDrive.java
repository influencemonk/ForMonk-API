package com.ForMonk2.utils;

import java.util.HashMap;

import org.bson.Document;

import com.ForMonk2.utils.CollectionUtils.Collections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

/*
 * Driver class to test other class functionalities
 */
public class TestDrive {
	
	public static void main(String[] args) {
		//MonkDBModel monkModel = new MonkDBModel("prikshit", "3284972874", "sdjfjjhdfjkkjsdf");
		
		String query = "{ $or: [ { username: '_soumyadeb3_'}, { username: 'tanmay'} ] }";
		
		HashMap<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("username", "soumyadeb1809");
		updateMap.put("accessToken", "dfsdfsdfsoumyadeb1809sdfsdf");
		
		//UpdateModel model = new UpdateModel(query, updateMap);
		
		@SuppressWarnings("unchecked")
		FindIterable<Document> iterable = (FindIterable<Document>) CollectionHandler.startOperation(query, Collections.MonkDB, Operations.read);
		MongoCursor<Document> cursor = iterable.iterator();
	    while(cursor.hasNext()) {
	    	System.out.println(cursor.next());
	    }
	}

}
