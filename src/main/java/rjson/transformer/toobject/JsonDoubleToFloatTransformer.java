package rjson.transformer.toobject;

import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

import com.daveayan.mirage.ReflectionUtils;

public class JsonDoubleToFloatTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		if(from == null) { return false; }
		return from instanceof Double && ReflectionUtils.classIsOfEitherType(to, Float.class, float.class);
	}
	public Object transform(Object from, Class<?> to, Context context) {
		Double fromDouble = (Double) from;
		return fromDouble.floatValue();
	}
}