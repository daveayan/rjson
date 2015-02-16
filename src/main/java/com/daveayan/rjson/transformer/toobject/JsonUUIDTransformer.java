package com.daveayan.rjson.transformer.toobject;

import java.util.UUID;

import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;

public class JsonUUIDTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		if(from == null) { return false; }
		return from instanceof String && ReflectionUtils.classIsOfEitherType(to, UUID.class);
	}

	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		return UUID.fromString((String) from);
	}
}
