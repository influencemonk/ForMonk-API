package com.ForMonk2.model;

import java.util.HashMap;

public class UpdateModel {
	
	private String query;
	private HashMap<String, Object> updateMap;
	
	public UpdateModel(String query, HashMap<String, Object> updateMap) {
		super();
		this.query = query;
		this.updateMap = updateMap;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public HashMap<String, Object> getUpdateMap() {
		return updateMap;
	}
	
	public void setUpdateMap(HashMap<String, Object> updateMap) {
		this.updateMap = updateMap;
	}
	
}
