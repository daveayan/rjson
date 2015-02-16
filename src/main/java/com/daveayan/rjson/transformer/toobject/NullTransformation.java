package com.daveayan.rjson.transformer.toobject;

import com.daveayan.json.JSONObject;
import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;

public class NullTransformation implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		if(from == null) {
			return true;
		}
		if(from instanceof String && from.toString().equals(JSONObject.NULL.toString())) {
			return true;
		}
		return false;
	}

	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		return null;
	}
}