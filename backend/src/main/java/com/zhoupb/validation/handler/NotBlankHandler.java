package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.NotBlank;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class NotBlankHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		NotBlank notBlank = (NotBlank) annotation;
		String str = (String) object;
		
		if ( str.length() == 0 )
			throw new ValidationException(notBlank.message());
	}

}
