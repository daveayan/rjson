package com.daveayan.rjson.transformer.toobject;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;

import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.transformers.Context;

public class JsonDoubleToFloatTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		if(from == null) { return false; }
		return from instanceof Double && ReflectionUtils.classIsOfEitherType(to, Float.class, float.class);
	}
	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		Double fromDouble = (Double) from;
		return fromDouble.floatValue();
	}
}