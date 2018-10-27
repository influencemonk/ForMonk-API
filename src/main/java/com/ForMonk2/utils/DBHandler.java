package com.ForMonk2.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class DBHandler {

	private MongoClient mongoClient;
	private static boolean connectionExists = false;
	private static MongoDatabase monkDB;

	protected DBHandler() {
		if (!connectionExists) {

			mongoClient = new MongoClient(Constants.DB_HOST, Constants.DB_PORT);

			@SuppressWarnings("unused")
			MongoCredential credential = MongoCredential.createCredential(Constants.DB_CREDENTIALS.USERNAME,
					Constants.DB_CREDENTIALS.DB_NAME, Constants.DB_CREDENTIALS.PASSWORD.toCharArray());

			monkDB = mongoClient.getDatabase(Constants.DB_CREDENTIALS.DB_NAME);

			connectionExists = true;

		}

	}

	protected static MongoDatabase getMonkDB() {

		if (monkDB != null) {

			return monkDB;

		} else {

			connectionExists = false;

			throw new RuntimeException("Sorry couldn't connect to " + Constants.DB_CREDENTIALS.DB_NAME);
		}
	}

}
