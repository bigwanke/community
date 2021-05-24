package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.Required;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class Requiredhandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		Required required = (Required) annotation;
		
		if ( object == null )
			throw new ValidationException(required.message());
	}

}
