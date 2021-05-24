package com.zhoupb.community.common;

public class Result<T> {
	
	private boolean success = false;
	
	private T data = null;
	
	private String message = null;
	
	public Result(boolean success, T data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static <T> Result<T> success(String message, T data) {
		return new Result<T>(true, data, message);
	}
	
	public static <T> Result<T> success(String message) {
		return new Result<T>(true, null, message);
	}
	
	public static <T>  Result<T> fail(String message) {
		return new Result<T>(false, null,message);
	}
	
}
