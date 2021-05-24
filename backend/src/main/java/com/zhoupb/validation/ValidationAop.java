package com.zhoupb.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.zhoupb.validation.exception.ValidationException;

@Aspect
public class ValidationAop {

	@Before("@annotation(com.zhoupb.validation.Validation)")
	public void validate(JoinPoint point) throws ValidationException, IllegalArgumentException, IllegalAccessException {
		Object args[] = point.getArgs();
		for (Object arg : args) {
			Class<?> argClass = arg.getClass();

			// 这个参数没有启用验证
			if ( argClass.getAnnotation(EnableValidator.class) == null )
				continue;
			
			// 获取所有的字段
			List<Field> fields = this.getAllFields(argClass);
			for (Field field : fields) {
				field.setAccessible(true);
				// 验证处理器
				this.validateHandler(field.getAnnotations(), field.get(arg));
			}
		}
	}
	
	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new LinkedList<>();

		while (clazz != Object.class) {
			fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}

		return fields;
	}
	
	private void validateHandler(Annotation annotations[], Object obj) {
		for (Annotation annotation : annotations) {
			// 这个注解有没有 Constraint 这个注解
			Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
			if ( constraint == null )
				continue;
			
			Class<?> validatedBy = constraint.validatedBy();
			// 容器中，获取验证器, 并且验证
			AnnotationValidator validator = ValidatorContainer.get(validatedBy);
			if ( validator != null )
				validator.validate(annotation, obj);
		}
	}

}
