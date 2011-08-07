package rjson.transformer.toobject;

import org.json.JSONObject;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class NullTransformation implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) {
			return true;
		}
		if(from instanceof String && from.toString().equals(JSONObject.NULL.toString())) {
			return true;
		}
		return false;
	}

	public Object transform(Object from, Class<?> to, Context context) {
		return null;
	}
}