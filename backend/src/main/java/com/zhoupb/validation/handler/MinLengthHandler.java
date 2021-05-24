package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.MinLength;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class MinLengthHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		MinLength min = (MinLength) annotation;
		String str = (String) object;
		
		if ( str.length() < min.min() )
			throw new ValidationException(String.format(min.message(), min.min()));
	}

}
