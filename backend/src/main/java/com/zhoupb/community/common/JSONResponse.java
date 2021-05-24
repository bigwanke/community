package com.zhoupb.community.common;

public class JSONResponse {
	
	private Integer code = null;
	
	private Object data = null;
	
	private String message = null;
	
	public JSONResponse(Integer code, Object data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public static JSONResponse ok(String message, Object data) {
		return new JSONResponse(200, data, message);
	}
	
	public static JSONResponse ok(String message) {
		return new JSONResponse(200, null, message);
	}
	
	public static JSONResponse error(Integer code, String message) {
		return new JSONResponse(code, null, message);
	}
	
}
