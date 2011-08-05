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
package rjson.transformer.tojson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public abstract class ReflectionBasedTransformer implements ObjectToJsonTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		ToJsonTransformationUtils.printer(context).printNewLine();
		ToJsonTransformationUtils.printer(context).indent();
		ToJsonTransformationUtils.printer(context).print("{");
		ToJsonTransformationUtils.printer(context).increaseIndent();
		ToJsonTransformationUtils.printer(context).printNewLine();
		if (object == null)
			return;

		ToJsonTransformationUtils.printClassName(object, ToJsonTransformationUtils.printer(context));

		reflectionBasedTransform(object, to, context);

		ToJsonTransformationUtils.printer(context).printNewLine();
		ToJsonTransformationUtils.printer(context).decreaseIndent();
		ToJsonTransformationUtils.printer(context).indent();
		ToJsonTransformationUtils.printer(context).print("}");
	}

	public abstract void reflectionBasedTransform(Object from, Class<?> to, Context context);

	public boolean include(Field field) {
		return true;
	}

	public boolean exclude(Field field) {
		return false;
	}

	public boolean include(Method method) {
		return true;
	}

	public String transform(Object from, Class<?> to, Context context) {
		transformToJson(from, to, context);
		return null;
	}
}