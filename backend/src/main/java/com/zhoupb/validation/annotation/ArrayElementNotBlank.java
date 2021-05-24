package com.zhoupb.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhoupb.validation.Constraint;
import com.zhoupb.validation.handler.ArrayElementNotBlankHandler;

/**
 * 数组中的元素不能有空字符串
 * 
 * @type String[]
 * 
 * @author zhoupb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArrayElementNotBlankHandler.class)
public @interface ArrayElementNotBlank {

	public String message();
	
}
