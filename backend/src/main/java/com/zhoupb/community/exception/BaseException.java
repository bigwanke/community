package com.zhoupb.community.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = -8299507273758537305L;
	
	private int code = 0;
	
	private String message = null;
	

	public BaseException() {
		super();
	}
	
	public BaseException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

}
