package com.daveayan.rjson.transformer.tojson;

import com.daveayan.transformers.CanTransform;
import com.daveayan.transformers.Context;

public class IgnoreClassTransformation implements CanTransform {
	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		return null;
	}
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		return from instanceof java.lang.Class<?>;
	}
}