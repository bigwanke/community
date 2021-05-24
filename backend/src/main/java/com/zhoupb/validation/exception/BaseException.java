package com.zhoupb.validation.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4845031613901276001L;
	
	private String message = null;
	
	public BaseException() {
		super();
	}
	
	public BaseException(String message) {
		super(message);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
