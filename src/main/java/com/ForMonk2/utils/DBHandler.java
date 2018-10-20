package com.ForMonk2.utils;

import java.net.UnknownHostException;

import com.mongodb.MongoClient; 

public class DBHandler {
	
	
	private MongoClient mongoClient;
	
	protected DBHandler() {
		try {
			mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private boolean checkIfConnectionExists() {
		return false;
	}
}
