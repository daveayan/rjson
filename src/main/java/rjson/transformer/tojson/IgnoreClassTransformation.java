package rjson.transformer.tojson;

import transformers.CanTransform;
import transformers.Context;

public class IgnoreClassTransformation implements CanTransform {
	public Object transform(Object from, Class<?> to, Context context) {
		return null;
	}
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from instanceof java.lang.Class<?>;
	}
}