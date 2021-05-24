package com.zhoupb.validation.exception;

public class ValidationException extends BaseException {

	private static final long serialVersionUID = 1900344194107145422L;

	// 默认412
	private int code = 412;

	public ValidationException() {
		super();
	}
	
	public ValidationException(String message) {
		super(message);
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

}
