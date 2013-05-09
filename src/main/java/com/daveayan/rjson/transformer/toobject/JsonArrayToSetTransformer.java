package com.daveayan.rjson.transformer.toobject;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;

import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.transformers.Context;

public class JsonArrayToSetTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) { return false; }
		if (from instanceof JSONArray) {
			if (ReflectionUtils.classImplements(to, Set.class)) {
				return true;
			}
		}
		return false;
	}

	public Object transform(Object from, Class<?> to, Context context) {
		JSONArray ja = (JSONArray) from;
		Set<Object> set = new HashSet<Object>();
		for (Object item : ja.getList()) {
			set.add(context.transformer().delegateTransformation(item, to, context));
		}
		return set;
	}
}