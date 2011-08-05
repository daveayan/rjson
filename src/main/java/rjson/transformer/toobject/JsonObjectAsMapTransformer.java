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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson.transformer.toobject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class JsonObjectAsMapTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return (from instanceof JSONObject) && ! ((JSONObject) from).has("class") && to.getName().trim().equals("java.lang.Object");
	}

	public Object transform(Object from, Class<?> to, Context context) {
		JSONObject jo = (JSONObject) from;
		Map<Object, Object> newMap = new HashMap<Object, Object>();
		Iterator<?> iter = jo.getMap().keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			newMap.put(key, context.transformer().delegateTransformation(jo.getMap().get(key), to, context));
		}
		return newMap;
	}
}