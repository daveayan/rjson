package rjson.transformer.toobject;

import java.util.HashSet;
import java.util.Set;

import mirage.ReflectionUtils;

import org.json.JSONArray;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class JsonArrayToSetTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
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