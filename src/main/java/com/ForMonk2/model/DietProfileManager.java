package com.ForMonk2.model;

import org.bson.Document;

import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils.Collections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;

@SuppressWarnings("unchecked")
public class DietProfileManager {
	
	private Gson gson = null;
	
	{
		gson = new Gson();
	}
	
	
	public boolean isAlreadyAdded(String username) {
		boolean result = false;
		
		InfluencerMaster influencerMaster = new InfluencerMaster();
		influencerMaster.setUsername(username);
		
		FindIterable<Document> iterable = (FindIterable<Document>) CollectionHandler.startOperation(gson.toJson(influencerMaster), Collections.DietDB, Operations.read);
		
		if(iterable.iterator().hasNext()) {
			result = true;
		}
		
		return result;
	}
	
	public boolean addToDB(String username) {
		
		InfluencerMaster influencerMaster = new InfluencerMaster();
		influencerMaster.setUsername(username);
		
		CollectionHandler.startOperation(influencerMaster, Collections.DietDB, Operations.create);
		
		return true;
	}
	
}
