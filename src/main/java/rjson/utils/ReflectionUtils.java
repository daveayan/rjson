package rjson.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {
	public static Method getMethodFor(Object targetObject, String methodName, Object[] parameters) {
		return getMethodFor(targetObject.getClass(), methodName, parameters);
	}
	
	public static Method getMethodFor(Class<?> targetClass, String methodName, Object[] parameters) {
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().trim().equals(methodName)) {
				if (canCast(method.getParameterTypes(), parameters)) {
					return method;
				}
			}
		}
		return null;
	}

	public static boolean canCast(Class<?>[] to, Object[] from) {
		if(to == null || from == null)
			return false;
		if (to.length != from.length)
			return false;
		if (to.length == from.length) {
			int i = 0;
			for (Class<?> parameterClass : to) {
				if (from[i] != null && !canCast(parameterClass, from[i]))
					return false;
				i++;
			}
		}
		return true;
	}

	public static boolean canCast(Object to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from.getClass());
	}

	public static boolean canCast(Object to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from);
	}

	public static boolean canCast(Class<?> to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to, from.getClass());
	}

	public static boolean canCast(Class<?> to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return to.isAssignableFrom(from);
	}

	private ReflectionUtils() {
	}
}