package com.zhoupb.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhoupb.validation.Constraint;
import com.zhoupb.validation.handler.RegexHandler;

/**
 * 正则表达式
 * 不符合则验证通过
 * 
 * @type String
 * 
 * @author zhoupb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexHandler.class)
public @interface Regex {
	
	public String regex();
	
	public String message();
	
}
