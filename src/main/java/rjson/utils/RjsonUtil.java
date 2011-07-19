/*
 * Copyright (c) 2011 Ayan Dave http://www.linkedin.com/in/daveayan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * 1) The above copyright notice and this permission notice shall be included without any changes or alterations 
 * in all copies or substantial portions of the Software.
 * 2) The copyright notice part of the org.json package and its classes shall be honored.
 * 3) This software shall be used for Good, not Evil.
 * portions of the Software.
 * 
 * The copyright notice part of the org.json package and its classes shall be honored.
 * This software shall be used for Good, not Evil.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RjsonUtil {

	public static void setField(Field field, Object objectToBeReturned, Object value) {
		try {
			if (field.getType().isPrimitive() && value == null)
				return;
			if (field.getType().getName().trim().equals("java.util.Date"))
				return;
			if (value != null && value.getClass().getName().equals("java.lang.Double")) {
				if (field.getClass().getName().equals("java.lang.Float") || field.getType().getName().equals("float")) {
					Double dblValue = (Double) value;
					field.set(objectToBeReturned, dblValue.floatValue());
					return;
				}
			}
			field.set(objectToBeReturned, value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String escapeJsonCharactersIn(String string) {
		String newString = StringUtils.replace(string, "\"", "");
		newString = StringUtils.replace(newString, "[", "\\[");
		newString = StringUtils.replace(newString, "]", "\\]");
		newString = StringUtils.replace(newString, "{", "\\{");
		newString = StringUtils.replace(newString, "}", "\\}");
		return newString;
	}

	public static List<Field> getAllFieldsIn(Object object) {
		List<Field> classes = new ArrayList<Field>();
		classes.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		Class<?> superclass = object.getClass().getSuperclass();
		while (superclass != null) {
			classes.addAll(Arrays.asList(superclass.getDeclaredFields()));
			superclass = superclass.getSuperclass();
		}

		return classes;
	}

	public static List<Field> getAllFieldsSortedAscendingIn(Object object) {
		List<Field> allFields = getAllFieldsIn(object);

		Comparator<Field> comparator = new Comparator<Field>() {
			public int compare(Field f1, Field f2) {
				return f1.getName().trim().compareToIgnoreCase(f2.getName().trim());
			}
		};
		Collections.sort(allFields, comparator);
		return allFields;
	}

	public static boolean isAccessible(Field field) {
		return Modifier.isPublic(field.getModifiers());
	}

	public static void makeAccessible(Field field) {
		if (!isAccessible(field)) {
			field.setAccessible(true);
		}
	}

	public static Class<?> asClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object objectFor(Class clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object objectForClassForcibly(Class<?> clazz) {
		try {
			Constructor<?> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return c.newInstance();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object objectFor(String className) {
		return objectForClassForcibly(asClass(className));
	}
}