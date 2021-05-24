package com.zhoupb.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhoupb.validation.Constraint;
import com.zhoupb.validation.handler.ArrayRequiredHandler;

/**
 * 数组不能为null或者长度等于0
 * 
 * @type Object[]
 * 
 * @author zhoupb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArrayRequiredHandler.class)
public @interface ArrayRequired {

	public String message();
	
}
