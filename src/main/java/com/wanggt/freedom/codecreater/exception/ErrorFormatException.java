package com.wanggt.freedom.codecreater.exception;

public class ErrorFormatException extends RuntimeException{

	private static final long serialVersionUID = 7336984344364437964L;
	
	public ErrorFormatException() {
		super();
	}
	
	public ErrorFormatException(String message) {
		super(message);
	}
}
