
package com.ForMonk2.model;

import java.util.ArrayList;


import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ForMonk2.utils.CollectionHandler;
import com.ForMonk2.utils.CollectionUtils;
import com.ForMonk2.utils.CollectionUtils.DBCollections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.google.gson.Gson;

@Document(collection = "InstagramFollowerTrend")

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

	@CachePut
	public void addToCollection() {
		CollectionHandler.startOperation(this, CollectionUtils.DBCollections.InstagramFollowerTrend,
				CollectionUtils.Operations.create);
	}

	public Object getObjectIterable() {
		return CollectionHandler.startOperation(new Gson().toJson(this), DBCollections.InstagramFollowerTrend,
				Operations.read);
	}

	public void updateData(UpdateModel updateData) {

		CollectionHandler.startOperation(updateData, CollectionUtils.DBCollections.InstagramFollowerTrend,
				CollectionUtils.Operations.update);
	}
	
	

}