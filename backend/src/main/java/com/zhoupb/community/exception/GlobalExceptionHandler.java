package com.zhoupb.community.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhoupb.community.common.JSONResponse;
import com.zhoupb.validation.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(BaseException.class)
	public JSONResponse baseException(BaseException exception) {
		return JSONResponse.error(exception.getCode(), exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	public JSONResponse validationException(ValidationException exception) {
		return JSONResponse.error(exception.getCode(), exception.getMessage());
	}
	
}
