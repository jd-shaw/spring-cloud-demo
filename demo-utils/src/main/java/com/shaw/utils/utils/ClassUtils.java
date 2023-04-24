package com.shaw.utils.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shaw
 * @date 2015-3-10
 */
public abstract class ClassUtils {
	public static List<Field> getAllDeclaredFields(Class<?> clazz) {
		List<Class<?>> classes = org.apache.commons.lang3.ClassUtils.getAllSuperclasses(clazz);
		classes.add(clazz);
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> cls : classes) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
		}
		return fields;
	}
}
