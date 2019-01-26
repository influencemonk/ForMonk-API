package com.ForMonk2.model;

import java.util.ArrayList;


import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils;

public class FollowerTrendMasterModel {

 private String _id;
 private String imcId;
 private ArrayList < FTMData > data = new ArrayList < FTMData > ();


 // Getter Methods 

 public String get_id() {
  return _id;
 }

 public String getIMCId() {
  return imcId;
 }

 // Setter Methods 

 public void set_id(String _id) {
  this._id = _id;
 }

 public void setIMCId(String IMCId) {
  this.imcId = IMCId;
 }
 
 public ArrayList< FTMData > getData(){
	 return data;
 }
 
 public void setData(ArrayList<FTMData> data) {
	 this.data = data;
 }
 
 public static class FTMData{
	 private long timestamp;
	 
	 public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getFollowers() {
		return followers;
	}
	public void setFollowers(long followers) {
		this.followers = followers;
	}
	public long getMediaCount() {
		return mediaCount;
	}
	public void setMediaCount(long mediaCount) {
		this.mediaCount = mediaCount;
	}
	private long followers;
	private long mediaCount;
 }
 
 public void addToCollection() {
	 CollectionHandler.startOperation(this, CollectionUtils.DBCollections.FollowerTrendMaster, CollectionUtils.Operations.create);
 }

}