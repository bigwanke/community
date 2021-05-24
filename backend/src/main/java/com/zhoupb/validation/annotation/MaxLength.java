package com.zhoupb.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhoupb.validation.Constraint;
import com.zhoupb.validation.handler.MaxLengthHandler;

/**
 * 字符串最大长度不能超过max
 * 
 * @type String
 * 
 * @author zhoupb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxLengthHandler.class)
public @interface MaxLength {
	
	public int max();
	
	public String message();

}
