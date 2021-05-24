package com.zhoupb.community.exception;

public class SaveToDataBaseException extends BaseException {

	private static final long serialVersionUID = 3914015862355496219L;
	
	public SaveToDataBaseException() {
		super();
	}
	
	public SaveToDataBaseException(String message) {
		super(500, message);
	}

}
