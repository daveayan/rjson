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

import java.util.ArrayList;
import java.util.List;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;
import com.daveayan.json.JSONArray;
import com.daveayan.mirage.ReflectionUtils;

public class JsonArrayTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		if(from == null) { return false; }
		if(from instanceof JSONArray) {
			if(ReflectionUtils.classImplements(to, List.class)) {
				return true;
			}
		}
		return false;
	}

	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		JSONArray ja = (JSONArray) from;
		List<Object> newList = new ArrayList<Object>();
		for (Object item : ja.getList()) {
			newList.add(context.transformer().delegateTransformation(item, to, fieldName, context));
		}
		return newList;
	}
}