
package com.ForMonk2.utils;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.ForMonk2.model.NestedArrayFilterModel;
import com.ForMonk2.model.UpdateModel;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

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
				response = create(data, mongoCollection);
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

			case aggregate:
				response = findNestedArrayElement((NestedArrayFilterModel) data, mongoCollection);
				break;

			default:
				throw new RuntimeException("Invalid operation");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	/*
	 * Method to add a document in a collection
	 */
	private static ObjectId create(Object data, MongoCollection<Document> mongoCollection) {
		Gson gson = new Gson();
		String jsonData = gson.toJson(data);
		Document document = Document.parse(jsonData);
		mongoCollection.insertOne(document);
		if (document.get("_id") == null) {
			return null;
		} else {
			ObjectId id = (ObjectId) document.get("_id");
			return id;
		}
	}

	/*
	 * Method to update documents in a collection
	 */
	private static void update(UpdateModel updateModel, MongoCollection<Document> mongoCollection) {
		BasicDBObject updatedData = new BasicDBObject();

		for (String key : updateModel.getUpdateMap().keySet()) {
			if (key != "_id")
				updatedData.append(key, updateModel.getUpdateMap().get(key));

			// updatedData.append(key, updateModel.getUpdateMap().get(key));

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
			throw new RuntimeException("There is object to update");
		} else {
			throw new RuntimeException("Can't convert null BSON ");
		}

	}

	private static AggregateIterable<Document> findNestedArrayElement(NestedArrayFilterModel model,
			MongoCollection<Document> mongoCollection) {

		ArrayList<Bson> matchList = new ArrayList<Bson>();
		
		if( model.getMatchValues() != null) {
			for (String key : model.getMatchValues().keySet()) {
				try {
					matchList.add(Aggregates.match(Filters.eq(key, model.getMatchValues().get(key))));
				} catch (Exception e) {
					
				}
			}
		}

		matchList.add(Aggregates.unwind("$" + model.getListName()));
		
		if(model.getProjectValues() != null) {
			for (String key : model.getProjectValues().keySet()) {
				try {
					matchList.add(Aggregates
							.match(Filters.eq(model.getListName() + "." + key, model.getProjectValues().get(key))));
				} catch (Exception e) {
	
				}
			}
		}

		AggregateIterable<Document> output = mongoCollection.aggregate(matchList);

		return output;

	}

}
