package com.ForMonk2.utils;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class DBHandler {

	private MongoClient mongoClient;
	private static boolean connectionExists = false;
	private static MongoDatabase monkDB;

	protected DBHandler() {
		if (!connectionExists) {

			MongoClientURI uri = new MongoClientURI(Constants.DB_CREDENTIALS.USER1_CRED);

			mongoClient = new MongoClient(uri);

			@SuppressWarnings("unused")
			MongoCredential credential = MongoCredential.createCredential(Constants.DB_CREDENTIALS.USERNAME,
					Constants.DB_CREDENTIALS.DB_NAME, Constants.DB_CREDENTIALS.PASSWORD.toCharArray());

			CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries
							.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			monkDB = mongoClient.getDatabase(Constants.DB_CREDENTIALS.DB_NAME).withCodecRegistry(pojoCodecRegistry);

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
