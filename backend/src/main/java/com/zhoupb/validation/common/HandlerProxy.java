package com.zhoupb.validation.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.zhoupb.validation.exception.TypeException;

/**
 * 给 hanlder 们添加验证类型功能
 * 
 * @author zhoupb
 *
 */
public class HandlerProxy<T> implements InvocationHandler {
	
	private enum ValidatorType {
		Required,
		
		MaxLength,
		
		MinLength,
		
		NotBlank,
		
		Regex,
		
		ArrayRequired,
		
		ArrayElementNotBlank,
	}

	private T target = null;
	
	@SuppressWarnings("unchecked")
	public T newProxy(T target) {
		this.target = target;
		return (T) Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		this.checkType(args);
		return method.invoke(this.target, args);
	}
	
	private void checkType(Object args[]) {
		Annotation annotation = (Annotation) args[0];
		Object object = args[1];
		
		if ( object == null )
			return;
		
		switch (ValidatorType.valueOf(annotation.annotationType().getSimpleName())) {
			case MaxLength:
			case MinLength:
			case NotBlank:
			case Regex:
				if ( !(object instanceof String) )
					throw new TypeException(this.type(String.class, object.getClass()));
				break;
			case Required:
				if ( !(object instanceof Object) )
					throw new TypeException(this.type(Object.class, object.getClass()));
				break;
			case ArrayRequired:
				if ( !(object instanceof Object[]) )
					throw new TypeException(this.type(Object[].class, object.getClass()));
				break;
			case ArrayElementNotBlank:
				if ( !(object instanceof String[]) )
					throw new TypeException(this.type(String[].class, object.getClass()));
				break;
		}
	}
	
	/**
	 * 类型错误消息
	 * @param required 需要的类型
	 * @param current 当前的类型
	 * @return
	 */
	private String type(Class<?> required, Class<?> current) {
		return String.format("类型不是[%s], 而是[%s]", required.getName(), current.getName());
	}

}
