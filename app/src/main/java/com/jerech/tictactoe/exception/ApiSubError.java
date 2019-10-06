package com.jerech.tictactoe.exception;

public class ApiSubError {
	
	private String field;
	
	private Object value;
	
	private String message;

	public ApiSubError(String field, Object value, String message) {
		super();
		this.field = field;
		this.value = value;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
	
	



}