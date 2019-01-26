package com.ForMonk2.utils;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.ForMonk2.model.UpdateModel;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class CollectionHandler extends DBHandler {

	private CollectionHandler() {
	} // this class is always supposed to be a singleton

	private static CollectionHandler collectionHandler = null;

	/*
	 * Method to handle CRUD operations for a collection
	 */
	public static Object startOperation(Object data, DBCollections collection, Operations operation) {

		Object response = false;

		try {

			if (collectionHandler == null) {
				collectionHandler = new CollectionHandler();
			}

			System.out.println("Collection: " + collection.toString() + " Operation: " + operation.toString());

			MongoCollection<Document> mongoCollection = getMonkDB().getCollection(collection.toString());

			switch (operation) {

			case create:
				create(data, mongoCollection);
				response = true;
				break;

			case update:
				update((UpdateModel) data, mongoCollection);
				response = true;
				break;

			case delete:
				delete((String) data, mongoCollection);
				response = true;
				break;

			case read:
				response = read((String) data, mongoCollection);
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	/*
	 * Method to add a document in a collection
	 */
	private static void create(Object data, MongoCollection<Document> mongoCollection) {
		Gson gson = new Gson();
		String jsonData = gson.toJson(data);
		Document document = Document.parse(jsonData);
		mongoCollection.insertOne(document);
	}

	/*
	 * Method to update documents in a collection
	 */
	private static void update(UpdateModel updateModel, MongoCollection<Document> mongoCollection) {
		BasicDBObject updatedData = new BasicDBObject();

		for (String key : updateModel.getUpdateMap().keySet()) {
			if (key != "_id")
				updatedData.append(key, updateModel.getUpdateMap().get(key));
			
			//updatedData.append(key, updateModel.getUpdateMap().get(key));

		}

		BasicDBObject updatedDocument = new BasicDBObject();
		updatedDocument.append("$set", updatedData);

		Bson queryBson = BasicDBObject.parse(updateModel.getQuery());

		mongoCollection.updateMany(queryBson, updatedDocument);

	}

	/*
	 * Method to delete documents in a collection
	 */
	private static void delete(String queryString, MongoCollection<Document> mongoCollection) {
		Bson filter = BasicDBObject.parse(queryString);
		mongoCollection.deleteMany(filter);
	}

	/*
	 * Method to query a collection
	 */
	private static FindIterable<Document> read(String queryString, MongoCollection<Document> mongoCollection) {
		Bson bson = BasicDBObject.parse(queryString);
		return mongoCollection.find(bson);
	}

	@SuppressWarnings("unchecked")
	public static Document GetSingleData(Object mainObjectIterator) {

		if (!(mainObjectIterator instanceof FindIterable)) {
			throw new RuntimeException("Please provide a iterable");
		}

		FindIterable<Document> mainObject = (FindIterable<Document>) mainObjectIterator;

		if (mainObject != null && mainObject.iterator().hasNext()) {
			return mainObject.iterator().next();
		} else if (!mainObject.iterator().hasNext()) {
			return null;
		} else {
			throw new RuntimeException("Can't convert null BSON ");
		}

	}

}
