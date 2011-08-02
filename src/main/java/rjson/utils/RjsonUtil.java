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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import rjson.Rjson;
import transformers.Transformer;

public class RjsonUtil {

	public static Object fileAsObject(String filename) throws IOException {
		return Rjson.newInstance().fromJson(fileAsString(filename));
	}

	public static String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}

	public static Rjson completeSerializer() {
		return Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
	}

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
//			field.set(objectToBeReturned, value);
//			field.set(objectToBeReturned, Cast.convert(value, ReflectionUtils.getInstanceOfClassForcibly(field.getType())));
			Object transformedValue = Transformer.newInstance().transform(value, field.getType(), null);
			field.set(objectToBeReturned, transformedValue);
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
}