package com.neo.booyah.server.exceptions;

public class ExceptionResponse {
	private int statusCode;
	private String status;
	private String message;
	public ExceptionResponse() {
		
	}
	
	public ExceptionResponse(int statusCode, String status, String message) {
		this.setStatusCode(statusCode);
		this.setMessage(message);
		this.setStatus(status);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
