package com.ForMonk2.model;

import java.util.HashMap;

public class NestedArrayFilterModel {

	private String listName;
	
	private HashMap<String,Object> matchValues;
	
	private HashMap<String , Object> projectValues;
	
	public HashMap<String, Object> getMatchValues() {
		return matchValues;
	}

	public void setMatchValues(HashMap<String, Object> matchValues) {
		this.matchValues = matchValues;
	}

	public HashMap<String, Object> getProjectValues() {
		return projectValues;
	}

	public void setProjectValues(HashMap<String, Object> projectValues) {
		this.projectValues = projectValues;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public HashMap<String, Object> getValues() {
		return matchValues;
	}

	public void setValues(HashMap<String, Object> values) {
		this.matchValues = values;
	}

	
	
	
	
	
}
