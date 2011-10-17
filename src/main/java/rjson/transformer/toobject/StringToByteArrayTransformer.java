package rjson.transformer.toobject;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class StringToByteArrayTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) { return false; }
		return from instanceof String && "byte[]".equals(to.getCanonicalName());
	}

	public Object transform(Object from, Class<?> to, Context context) {
		return ((String) from).getBytes();
	}
}