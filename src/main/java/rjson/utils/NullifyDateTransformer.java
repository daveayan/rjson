package rjson.utils;

import rjson.transformer.tojson.FieldBasedTransformer;

import com.daveayan.transformers.Context;

public class NullifyDateTransformer extends FieldBasedTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		printData(null, context);
	}

	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from != null && from instanceof java.util.Date;
	}
}