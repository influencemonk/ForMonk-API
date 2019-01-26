package com.ForMonk2.services;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ForMonk2.helpers.InstagramDataHelper;
import com.ForMonk2.model.FollowerTrendMasterModel;
import com.ForMonk2.model.FollowerTrendMasterModel.FTMData;
import com.ForMonk2.model.ProfileSummaryGQLResponse;
import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionKeys;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.ForMonk2.utils.Constants.INSTA_SCRAPER.ApiUser;
import com.ForMonk2.utils.DateHandler;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;

@Component
public class UpdateFollowersServices {

 @SuppressWarnings("unchecked")
 @Scheduled(fixedRate = 5000)
 public void getDataFromIMC() {

  FindIterable < Document > allUsersData = null;

  Object allUsersIterable = CollectionHandler.startOperation("{}", DBCollections.InfluencerMasterCollection, Operations.read);

  if (allUsersIterable instanceof FindIterable) {

   allUsersData = (FindIterable < Document > ) allUsersIterable;


   if (allUsersData != null) {

    if (allUsersData.iterator().hasNext()) {
     Document doc = allUsersData.first();
     
     
     startGettingFollowers(doc.getString(CollectionKeys.InfluencerMasterCollection.socialHandle) , doc.getObjectId("_id").toString());
    }

   }
  }

 }

 private void startGettingFollowers(String userName , String IMCId) {
	 
	 printData("user id is "+IMCId);

     InstagramDataHelper instagramDataHelper = new InstagramDataHelper();

	 String userData = instagramDataHelper.getProfileSummaryGQL(userName, 1, ApiUser.imWeb);
	 
	 ProfileSummaryGQLResponse profileSummaryGQL = new Gson().fromJson(userData, ProfileSummaryGQLResponse.class);
	 
	 FollowerTrendMasterModel followerTrendMaster = new FollowerTrendMasterModel();
	 followerTrendMaster.setIMCId(IMCId);
	 
	 Object result = CollectionHandler.startOperation(new Gson().toJson(followerTrendMaster), DBCollections.FollowerTrendMaster, Operations.read);
	 
	 if(result == null ) {
		 printData("empty");
	 }else {
		 followerTrendMaster = CollectionHandler.<FollowerTrendMasterModel>GetSingleData
				 (
						 result
				 );
		 
		 if(followerTrendMaster == null ) {
			 
			 FollowerTrendMasterModel updateTrendMaster = new FollowerTrendMasterModel();
			 updateTrendMaster.setIMCId(IMCId);
			 
			 FTMData data = getCurrentProfileData(profileSummaryGQL);
			 
			 ArrayList<FTMData> dataList = new ArrayList<FTMData>();
			 dataList.add(data);
			 
			 updateTrendMaster.setData(dataList);
			 
			 updateTrendMaster.addToCollection();
			 
			 printData(updateTrendMaster);
			 
		 }else {
			 ArrayList<FTMData> dataList = followerTrendMaster.getData();
			 
			 FTMData data = getCurrentProfileData(profileSummaryGQL);
			 
			 dataList.add(data);
			 
			 followerTrendMaster.setData(dataList);
			 
			 
			 
		 }
	 }
	 
	 
	 
 }
 
 private FTMData getCurrentProfileData(ProfileSummaryGQLResponse profileSummaryGQL) {
	 
	 FTMData data = new FTMData(); 
	 
	 data.setFollowers((long)profileSummaryGQL.getData().getFollowed_by());
	 data.setMediaCount((long)profileSummaryGQL.getData().getMedia_count());
	 data.setTimestamp(DateHandler.getCurrentTimeStamp());
	 
	 return data;
 }
 
 private void printData(Object object) {
	 System.out.println(new Gson().toJson(object));
 }

}