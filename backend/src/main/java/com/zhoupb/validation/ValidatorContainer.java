package com.zhoupb.validation;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidatorContainer {
	
	private static final Map<Class<AnnotationValidator>, AnnotationValidator> validates = new HashMap<>();
	
	static {
		List<Class<?>> clazzs = getAllClass();
		if ( clazzs != null )
			for (Class<?> clazz : clazzs)
				try {
					register((AnnotationValidator) clazz.getConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
	}
	
	@SuppressWarnings("unchecked")
	public static void register(AnnotationValidator validator) {
		if ( !(validator instanceof AnnotationValidator) )
			return;
		validates.put((Class<AnnotationValidator>) validator.getClass(), validator);
//		暂时不需要验证字段类型
//		HandlerProxy<AnnotationValidator> proxy = new HandlerProxy<>();
//		AnnotationValidator tmp = proxy.newProxy(validator);
//		validates.put((Class<AnnotationValidator>) validator.getClass(), tmp);
	}
	
	public static AnnotationValidator get(Class<?> clazz) {
		return validates.get(clazz);
	}

	private static List<Class<?>> getAllClass() {
		List<Class<?>> clazzs = new ArrayList<Class<?>>();
		String packageName = ValidatorContainer.class.getPackage().getName() + ".handler";
		String handlerPath = (ValidatorContainer.class.getResource("").getPath() + "handler");
		
		File dir = new File(handlerPath);
		
		if ( !dir.exists() || !dir.isDirectory() )
			return null;
		
		File files[] = dir.listFiles(pathname -> pathname.getName().endsWith(".class"));
		for (File file : files) {
			String className = file.getName().substring(0, file.getName().length() - 6);
			try {
				Class<?> clazz = Class.forName(packageName + '.' + className);
				Validator validator = clazz.getAnnotation(Validator.class);
				if ( validator != null )
					clazzs.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return clazzs;
	}
	
}
