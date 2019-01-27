package com.ForMonk2.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.ForMonk2.model.IMCModel;
import com.ForMonk2.model.IMCSocialAccount;
import com.ForMonk2.model.NestedArrayFilterModel;
import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.mongodb.client.AggregateIterable;

public class IMCDataHelper {
	
	@SuppressWarnings("unchecked")
	public String getIMCObjectId(String clientId, String socialHandle) {
		
		NestedArrayFilterModel filter = new NestedArrayFilterModel();
		
		filter.setListName("socialAccounts");
		HashMap<String, Object> matchValuesMap = new HashMap<String, Object>();
		matchValuesMap.put("socialAccounts.socialHandle", "_soumyadeb_");
		filter.setMatchValues(matchValuesMap);
		
		AggregateIterable<Document> searchResult = (AggregateIterable<Document>) CollectionHandler
											  		.startOperation(filter, DBCollections.InfluencerMasterCollection, Operations.aggregate);
		
		ObjectId imcId = null;
		
		if(searchResult.iterator().hasNext()) {
			Document resultDoc = searchResult.iterator().next();
			imcId = (ObjectId) resultDoc.get("_id");
		}
		else {
			imcId = addSocialHandle(clientId, socialHandle);
		}
		
		return imcId.toString();
	}
	
	private ObjectId addSocialHandle(String clientId, String socialHandle) {
		
		IMCSocialAccount imcSocialAccount = new IMCSocialAccount();
		imcSocialAccount.setClientId(clientId);
		imcSocialAccount.setSocialHandle(socialHandle);
		
		List<IMCSocialAccount> socialAccountList = new ArrayList<IMCSocialAccount>();
		socialAccountList.add(imcSocialAccount);
		
		IMCModel imcRecord = new IMCModel();
		imcRecord.setSocialAccounts(socialAccountList);
		
		ObjectId imcId = (ObjectId) CollectionHandler.startOperation(imcRecord, DBCollections.InfluencerMasterCollection, Operations.create);
		
		return imcId;
	}

}
