package com.ForMonk2.model;

public class MonkDBModel {
	private String username;
	private String id;
	private String accessToken;	
	
	public MonkDBModel(String username, String id, String accessToken) {
		super();
		this.username = username;
		this.id = id;
		this.accessToken = accessToken;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
