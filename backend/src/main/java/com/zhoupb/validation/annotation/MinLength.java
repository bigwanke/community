package com.zhoupb.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhoupb.validation.Constraint;
import com.zhoupb.validation.handler.MinLengthHandler;

/**
 * 字符串最小长度不能小于min
 * 
 * @type String
 * 
 * @author zhoupb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinLengthHandler.class)
public @interface MinLength {

	public int min();
	
	public String message();
	
}
