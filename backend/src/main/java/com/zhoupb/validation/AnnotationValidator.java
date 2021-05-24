package com.zhoupb.validation;

import java.lang.annotation.Annotation;

import com.zhoupb.validation.exception.ValidationException;

public interface AnnotationValidator {

	public void validate(Annotation annotation, Object object) throws ValidationException;
	
}
