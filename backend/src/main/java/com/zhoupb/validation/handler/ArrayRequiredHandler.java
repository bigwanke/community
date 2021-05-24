package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.ArrayRequired;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class ArrayRequiredHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		ArrayRequired arrayRequired = (ArrayRequired) annotation;
		Object array[] = (Object[]) object;
		
		if ( array == null || array.length == 0 )
			throw new ValidationException(arrayRequired.message());
	}

}
