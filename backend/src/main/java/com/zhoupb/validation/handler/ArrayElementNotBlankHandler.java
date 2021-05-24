package com.zhoupb.validation.handler;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.AnnotationValidator;
import com.zhoupb.validation.Validator;
import com.zhoupb.validation.annotation.ArrayElementNotBlank;
import com.zhoupb.validation.exception.ValidationException;

@Validator
public class ArrayElementNotBlankHandler implements AnnotationValidator {

	@Override
	public void validate(Annotation annotation, Object object) throws ValidationException {
		ArrayElementNotBlank arrayElementNotBlank = (ArrayElementNotBlank) annotation;
		String arrays[] = (String[]) object;
		
		for ( int i = 0; i < arrays.length; i++ )
			if ( arrays[i] == null || arrays[i].length() == 0 )
				throw new ValidationException(String.format(arrayElementNotBlank.message(), i + 1));
	}

}
