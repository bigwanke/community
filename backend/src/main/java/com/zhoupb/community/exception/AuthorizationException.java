package com.zhoupb.community.exception;

public class AuthorizationException extends BaseException {

	private static final long serialVersionUID = -3600343684378104802L;
	
	public AuthorizationException() {
		super();
	}
	
	public AuthorizationException(String message) {
		super(401, message);
	}
	
	public AuthorizationException(String message, Integer code) {
		super(code, message);
	}

}
