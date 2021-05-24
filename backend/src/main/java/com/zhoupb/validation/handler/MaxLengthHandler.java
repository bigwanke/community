package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.MaxLength;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class MaxLengthHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		MaxLength max = (MaxLength) annotation;
		String str = (String) object;
		
		if ( str.length() > max.max() )
			throw new ValidationException(String.format(max.message(), max.max()));
	}
	
}
