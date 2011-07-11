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
package rjson.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.utils.RjsonUtil;

public class FieldBasedTransformer extends ReflectionBasedTransformer implements JsonToObjectTransformer {

	public Object transformJsonToObject(Object object, Rjson rjson) {
		JSONObject jo = (JSONObject) object;
		try {
			Object objectToBeReturned = RjsonUtil.objectFor(jo.getString("class"));
			List<Field> fields = RjsonUtil.getAllFieldsIn(objectToBeReturned);
			Iterator<Field> iter = fields.iterator();
			while (iter.hasNext()) {
				Field field = iter.next();
				RjsonUtil.makeAccessible(field);
				Object jsonField = jo.get(field.getName());
				String jsonFieldContents = jsonField.toString();
				System.out.println("***===>>>" + field.getType().getName() + " : " + " : " + jsonField.getClass().getName() + " : " + jsonFieldContents
						+ " <<<===***");
				Object returnedObject = rjson.jsonObjectToObjectControl(jsonField);
				RjsonUtil.setField(field, objectToBeReturned, returnedObject);
			}
			return objectToBeReturned;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void reflectionBasedTransform(Object object, Printer printer, Rjson rjson) {
		List<Field> fields = RjsonUtil.getAllFieldsIn(object);
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
				if (rjson.ignoreModifiers() || RjsonUtil.isAccessible(field)) {
					RjsonUtil.makeAccessible(field);
					try {
						Object newObject = field.get(object);
						if (exclude(field)) {
							continue;
						}
						if (include(field)) {
							if (pendingHasMoreElements) {
								hasMoreElements(printer);
								pendingHasMoreElements = false;
							}
							printFieldName(field.getName(), printer);
							delegateHandlingOf(newObject, printer, rjson);
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

	public boolean canHandle(Object object) {
		return true;
	}
}