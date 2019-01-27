package com.ForMonk2.utils;


import java.util.HashMap;

import org.bson.Document;

import com.ForMonk2.helpers.IMCDataHelper;
import com.ForMonk2.model.NestedArrayFilterModel;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.mongodb.client.AggregateIterable;

/*
 * Driver class to test other class functionalities
 */
public class TestDrive {
	
	public static void main(String[] args) {
		/*InstaCrawlerHelper helper = new InstaCrawlerHelper();
		helper.getProfileData("ishaanshakunt");*/
		
		//IMCDataHelper manager = new IMCDataHelper();
		//System.out.println("Object ID: "+manager.addSocialHandle("CMCCULQOUVBFRZFFEWSGRHDZYDSTRUNVIPMFMITSPRQHHNNWWHFOBPYYDABA", "deepikapadukone"));
		
		NestedArrayFilterModel filter = new NestedArrayFilterModel();
		
		filter.setListName("socialAccounts");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("socialAccounts.socialHandle", "_soumyadeb_");
		filter.setMatchValues(map);
		
		AggregateIterable<Document> output = (AggregateIterable<Document>) CollectionHandler.startOperation(filter, DBCollections.InfluencerMasterCollection, Operations.aggregate);
		
		for(Document doc: output) {
			System.out.println("OP: " + doc.toJson());
		}
	}

}
