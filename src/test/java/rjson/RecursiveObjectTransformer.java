package rjson;

import java.lang.reflect.Field;

import rjson.transformer.tojson.FieldBasedTransformer;
import transformers.Context;

public class RecursiveObjectTransformer extends FieldBasedTransformer {
	@Override
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from != null && from instanceof rjson.domain.RecursiveObject;
	}

	public boolean exclude(Field field) {
		return field.getType().getName().equals("rjson.domain.RecursiveObject");
	}
}