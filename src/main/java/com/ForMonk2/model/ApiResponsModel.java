package com.ForMonk2.model;

public class ApiResponsModel<T> {

	private T serverObject;
	private boolean error;
	private String message;
	
	public T getServerObject() {
		return serverObject;
		
	}
	
	public void setServerObject(T serverObject) {
		this.serverObject = serverObject;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public void setMessage(String message) {
		this.message = message;
		
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getError() {
		return error;
	}
	
}
