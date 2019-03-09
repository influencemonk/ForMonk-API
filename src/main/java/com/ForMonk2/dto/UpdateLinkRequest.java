package com.ForMonk2.dto;

public class UpdateLinkRequest {

	private String linkTitle;
	private String postImageUrl;
	private String plId;
	private String imcId;
	private String linkUrl;
	
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getPostImageUrl() {
		return postImageUrl;
	}
	public void setPostImageUrl(String postImageUrl) {
		this.postImageUrl = postImageUrl;
	}
	public String getPlId() {
		return plId;
	}
	public void setPlId(String plId) {
		this.plId = plId;
	}
	public String getImcId() {
		return imcId;
	}
	public void setImcId(String imcId) {
		this.imcId = imcId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
}
