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

import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public class LeafNumberTransformer implements ObjectToJsonTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, (StringBuffer) context.get("json_buffer"));
			return;
		}
		ToJsonTransformationUtils.printData(object, (StringBuffer) context.get("json_buffer"));
	}
	
	public String transform(Object from, Class<?> to, Context context) {
		if (from == null) {
			ToJsonTransformationUtils.printData(null, (StringBuffer) context.get("json_buffer"));
		} else {
			ToJsonTransformationUtils.printData(from, (StringBuffer) context.get("json_buffer"));
		}
		return null;
	}
	
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if (from == null) {
			return true;
		}
		if (from instanceof java.lang.Number) {
			return true;
		}
		return false;
	}
}