package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.Regex;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class RegexHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		Regex regex = (Regex) annotation;
		String str = (String) object;
		
		if ( !str.matches(regex.regex()) ) 
			throw new ValidationException(regex.message());
	}

}
