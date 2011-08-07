package rjson.transformer.toobject;

import java.util.Date;

import mirage.ReflectionUtils;

import org.json.JSONObject;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class JsonStringToDateTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from instanceof String && ReflectionUtils.classIsOfEitherType(to, Date.class);
	}

	public Object transform(Object from, Class<?> to, Context context) {
		String string = (String) from;
		if (string != null && string.equals(JSONObject.NULL.toString()))
			return null;
		return new Date(string);
	}
}