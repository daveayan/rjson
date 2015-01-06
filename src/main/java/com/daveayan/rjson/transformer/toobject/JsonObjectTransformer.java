/*
 * Copyright (c) 2011 Ayan Dave http://daveayan.com
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.daveayan.rjson.transformer.toobject;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;
import com.daveayan.json.JSONException;
import com.daveayan.json.JSONObject;
import com.daveayan.mirage.ReflectionUtils;

public class JsonObjectTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) { return false; }
		return (from instanceof JSONObject) && (((JSONObject) from).has("jvm_class_name") || to.getName().trim().equals("java.lang.Object"));
	}

	public Object transform(Object from, Class<?> to, Context context) {
		JSONObject jo = (JSONObject) from;
		try {
			if(jo == null || jo.getString("jvm_class_name") == null) {
				System.out.println("HERE");
			}
			Object objectToBeReturned = ReflectionUtils.objectFor(jo.getString("jvm_class_name"));
			List<Field> fields = ReflectionUtils.getAllFieldsIn(objectToBeReturned);
			Iterator<Field> iter = fields.iterator();
			while (iter.hasNext()) {
				Field field = iter.next();
				ReflectionUtils.makeAccessible(field);
				if (jo.has(field.getName())) {
					Object jsonField = jo.get(field.getName());
					Object returnObject = context.transformer().delegateTransformation(jsonField, field.getType(), context);
					try {
						if(returnObject != null) {
							if(returnObject.getClass().getName().trim().equals("org.json.JSONObject$Null")) {
								returnObject = null;
							}
						}
						field.set(objectToBeReturned, returnObject);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException iae) {
						iae.printStackTrace();
					}
				}
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
}