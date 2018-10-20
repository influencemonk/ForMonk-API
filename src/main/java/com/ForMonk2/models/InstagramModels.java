package com.ForMonk2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstagramModels {
	


	public static class OAuthExceptionModel {

	@SerializedName("error_type")
	@Expose
	private String errorType;
	@SerializedName("code")
	@Expose
	private Integer code;
	@SerializedName("error_message")
	@Expose
	private String errorMessage;

	public String getErrorType() {
	return errorType;
	}

	public void setErrorType(String errorType) {
	this.errorType = errorType;
	}

	public Integer getCode() {
	return code;
	}

	public void setCode(Integer code) {
	this.code = code;
	}

	public String getErrorMessage() {
	return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
	}

	}
}
