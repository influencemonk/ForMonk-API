package com.ForMonk2.model;

import org.springframework.data.annotation.Id;

import com.ForMonk2.utils.DateHandler;

public class BaseModel {
	
	@Id
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	private String createOn = DateHandler.getCurrentUTCTime();

}
