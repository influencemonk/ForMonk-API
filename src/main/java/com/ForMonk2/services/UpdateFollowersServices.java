
package com.ForMonk2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ForMonk2.helpers.InstagramDataHelper;
import com.ForMonk2.model.FollowerTrendMasterModel;
import com.ForMonk2.model.FollowerTrendMasterModel.FTMData;
import com.ForMonk2.model.ProfileSummaryGQLResponse;
import com.ForMonk2.model.UpdateModel;
import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.ForMonk2.utils.Constants.INSTA_SCRAPER.ApiUser;
import com.ForMonk2.utils.DateHandler;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;

//@Component
public class UpdateFollowersServices {

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 24*60*60*1000)

	public void getDataFromIMC() {
		
		FindIterable<Document> allUsersData = null;

		Object allUsersIterable = CollectionHandler.startOperation("{}", DBCollections.InfluencerMasterCollection,
				Operations.read);

		if (allUsersIterable instanceof FindIterable) {

			allUsersData = (FindIterable<Document>) allUsersIterable;

			if (allUsersData != null) {

				for(Document doc : allUsersData) {
					
					List<Document> socialAccounts = (List<Document>)doc.get("socialAccounts");
					
					for(Document socialAccount : socialAccounts) {
						startGettingFollowers(socialAccount.getString("socialHandle") , doc.getObjectId("_id").toString());
					}
					
				}

			}
		}

	}

	private void startGettingFollowers(String userName, String IMCId) {

		printData("user id is " + IMCId);

		InstagramDataHelper instagramDataHelper = new InstagramDataHelper();

		String userData = instagramDataHelper.getProfileSummaryGQL(userName, 1, ApiUser.imWeb).toJSONString();

		ProfileSummaryGQLResponse profileSummaryGQL = new Gson().fromJson(userData, ProfileSummaryGQLResponse.class);

		FollowerTrendMasterModel followerTrendMaster = new FollowerTrendMasterModel();
		followerTrendMaster.setIMCId(IMCId);

		Object temp = followerTrendMaster.getDBObject();

		if (temp == null) {

			FollowerTrendMasterModel updateTrendMaster = new FollowerTrendMasterModel();
			updateTrendMaster.setIMCId(IMCId);

			FTMData data = getCurrentProfileData(profileSummaryGQL);

			ArrayList<FTMData> dataList = new ArrayList<FTMData>();
			dataList.add(data);

			updateTrendMaster.setData(dataList);

			updateTrendMaster.addToCollection();

			printData(updateTrendMaster);

		} else {
			
			followerTrendMaster = (FollowerTrendMasterModel)temp;
			
			ArrayList<FTMData> dataList = followerTrendMaster.getData();

			FTMData data = getCurrentProfileData(profileSummaryGQL);

			dataList.add(data);

			followerTrendMaster.setData(dataList);

			HashMap<String, Object> updateMap = new HashMap<String, Object>();

			updateMap.put("data", dataList);

			FollowerTrendMasterModel updateSet = new FollowerTrendMasterModel();
			updateSet.setIMCId(IMCId);

			UpdateModel updateModel = new UpdateModel(new Gson().toJson(updateSet), updateMap);

			followerTrendMaster.updateData(updateModel);

		}

	}

	private FTMData getCurrentProfileData(ProfileSummaryGQLResponse profileSummaryGQL) {

		FTMData data = new FTMData();

		data.setFollowers((long) profileSummaryGQL.getData().getFollowed_by());
		data.setMediaCount((long) profileSummaryGQL.getData().getMedia_count());
		data.setTimestamp(DateHandler.getCurrentTimeStamp());

		return data;
	}

	private void printData(Object object) {
		System.out.println(new Gson().toJson(object));
	}

}