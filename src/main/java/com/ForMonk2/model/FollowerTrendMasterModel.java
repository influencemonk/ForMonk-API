
package com.ForMonk2.model;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;

public class FollowerTrendMasterModel {

	@BsonProperty("_id")
	private String _id;

	@BsonProperty("imcId")
	private String imcId;

	@BsonProperty("data")
	private ArrayList<FTMData> data;

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

	public ArrayList<FTMData> getData() {
		return data;
	}

	public void setData(ArrayList<FTMData> data) {
		this.data = data;
	}

	public static class FTMData {
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

		@BsonProperty("followers")
		private long followers;

		@BsonProperty("mediaCount")
		private long mediaCount;
	}

	public void addToCollection() {
		CollectionHandler.startOperation(this, CollectionUtils.DBCollections.InstagramFollowerTrend,
				CollectionUtils.Operations.create);
	}

	private Object getObjectIterable() {
		return CollectionHandler.startOperation(new Gson().toJson(this), DBCollections.InstagramFollowerTrend,
				Operations.read);
	}

	@SuppressWarnings("unchecked")
	public FollowerTrendMasterModel getDBObject() {
		Document document = CollectionHandler.GetSingleData(getObjectIterable());
		_id = document.getObjectId("_id").toString();
		imcId = document.getString("imcId");
		data = (ArrayList<FTMData>) document.get("data");

		return this;

	}

	public void updateData(UpdateModel updateData) {

		CollectionHandler.startOperation(updateData, CollectionUtils.DBCollections.InstagramFollowerTrend,
				CollectionUtils.Operations.update);
	}
	
	

}