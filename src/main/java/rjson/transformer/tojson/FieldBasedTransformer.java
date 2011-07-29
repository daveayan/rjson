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
package rjson.transformer.tojson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import mirage.ReflectionUtils;
import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.ToJsonTransformationUtils;

public class FieldBasedTransformer extends ReflectionBasedTransformer {
	@Override
	public void reflectionBasedTransform(Object object, Printer printer, Rjson rjson) {
		List<Field> fields = ReflectionUtils.getAllFieldsIn(object);
		boolean pendingHasMoreElements = false;
		if (fields != null) {
			Iterator<Field> iter = fields.iterator();
			while (true) {
				if (!iter.hasNext())
					break;
				Field field = iter.next();
				if (Modifier.isTransient(field.getModifiers()))
					continue;
				if (Modifier.isFinal(field.getModifiers()))
					continue;
				if (rjson.ignoreModifiers() || ReflectionUtils.isAccessible(field)) {
					ReflectionUtils.makeAccessible(field);
					try {
						Object newObject = field.get(object);
						if (exclude(field)) {
							continue;
						}
						if (include(field)) {
							if (pendingHasMoreElements) {
								ToJsonTransformationUtils.hasMoreElements(printer);
								pendingHasMoreElements = false;
							}
							ToJsonTransformationUtils.printFieldName(field.getName(), printer);
							ToJsonTransformationUtils.delegateHandlingOf(newObject, printer, rjson);
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (include(field) && iter.hasNext()) {
						pendingHasMoreElements = true;
					}
				}
			}
		}
	}

	public boolean canConvertToJson(Object object) {
		return true;
	}
}