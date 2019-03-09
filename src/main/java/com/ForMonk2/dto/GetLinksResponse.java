package com.ForMonk2.dto;

import java.util.List;

import com.ForMonk2.entity.ProfileLink;

public class GetLinksResponse {
	
	private int count;
	private List<ProfileLink> profileLinks;
	
	public List<ProfileLink> getProfileLinks() {
		return profileLinks;
	}
	public void setProfileLinks(List<ProfileLink> profileLinks) {
		this.profileLinks = profileLinks;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
