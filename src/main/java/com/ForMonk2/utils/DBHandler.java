package com.ForMonk2.utils;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential; 

public class DBHandler{
	
	
	private MongoClient mongoClient;
	private boolean connectionExists = false;
	private DB monkDB;

	protected DBHandler() {
		try {

			mongoClient = new MongoClient();

			if(! connectionExists) {
				
				mongoClient = new MongoClient(Constants.DB_HOST , Constants.DB_PORT);
				
				MongoCredential credential = MongoCredential.createMongoCRCredential(Constants.DB_CREDENTIALS.USERNAME,
																 Constants.DB_CREDENTIALS.DB_NAME , 
																 Constants.DB_CREDENTIALS.PASSWORD.toCharArray());
				
				GeneralUtils.printStackTrace("Connection created successfully");
				
				monkDB =  mongoClient.getDB(Constants.DB_CREDENTIALS.DB_NAME);
				
				connectionExists = true;
				
				
			}
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
			connectionExists = false;
		}
		
	}
	

	private DB getMonkDB(){
		
		if(monkDB != null ) {
			
			return monkDB;
			
		}else {
			
			connectionExists = false;
			
			throw new RuntimeException("Sorry couldn't connect to "+Constants.DB_CREDENTIALS.DB_NAME);
		}
	}
	
}
