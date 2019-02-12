package com.ForMonk2.model;

import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "InfluencerMasterCollection")
public class IMCModel {

	@Id
	private String id;
	
	private List<IMCSocialAccount> socialAccounts;

	public List<IMCSocialAccount> getSocialAccounts() {
		return socialAccounts;
	}

	public void setSocialAccounts(List<IMCSocialAccount> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
	
	public String getId() {
		return id;
	}
	
}
