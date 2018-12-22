package com.ForMonk2.model;

public class InstaComment {
	
	private String id;
	private String text;
	private long timestamp;
	private String owner;
	
	public InstaComment(String id, String text, long timestamp, String owner) {
		super();
		this.id = id;
		this.text = text;
		this.timestamp = timestamp;
		this.owner = owner;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	

}
