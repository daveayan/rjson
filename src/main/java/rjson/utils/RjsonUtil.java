/*
 * Copyright (c) 2011 Ayan Dave http://www.linkedin.com/in/daveayan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

public class RjsonUtil {
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
	
	public static boolean isAccessible(Field field) {
		return Modifier.isPublic(field.getModifiers());
	}
	
	public static void makeAccessible(Field field) {
		if (! isAccessible(field)) {
			field.setAccessible(true);
		}
	}
	
	public static Class asClass(String className) {
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
	
	public static Object objectFor(String className) {
		return objectFor(asClass(className));
	}
}