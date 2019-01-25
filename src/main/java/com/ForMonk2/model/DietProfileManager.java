package com.ForMonk2.model;

import org.bson.Document;

import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;

@SuppressWarnings("unchecked")
public class DietProfileManager {
	
	private Gson gson = null;
	
	{
		gson = new Gson();
	}
	
	
	public boolean isAlreadyAdded(String username, DBCollections collection) {
		boolean result = false;
		
		InfluencerMaster influencerMaster = new InfluencerMaster();
		influencerMaster.setUsername(username);
		
		FindIterable<Document> iterable = (FindIterable<Document>) CollectionHandler.startOperation(gson.toJson(influencerMaster), collection, Operations.read);
		
		if(iterable.iterator().hasNext()) {
			result = true;
		}
		
		return result;
	}
	
	public boolean addToDB(String username, DBCollections collection) {
		
		InfluencerMaster influencerMaster = new InfluencerMaster();
		influencerMaster.setUsername(username);
		
		CollectionHandler.startOperation(influencerMaster, collection, Operations.create);
		
		return true;
	}
	
}
