package com.daveayan.rjson.transformer.toobject;

import java.util.List;
import java.util.Vector;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.json.JSONArray;
import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.transformers.Context;

public class JsonArrayToVectorTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) { return false; }
		if (from instanceof JSONArray) {
			if (ReflectionUtils.classImplements(to, Vector.class)) {
				return true;
			}
		}
		return false;
	}

	public Object transform(Object from, Class<?> to, Context context) {
		JSONArray ja = (JSONArray) from;
		List<Object> vector = new Vector<Object>();
		for (Object item : ja.getList()) {
			vector.add(context.transformer().delegateTransformation(item, to, context));
		}
		return vector;
	}
}