package rjson.utils;

import rjson.transformer.ToJsonTransformationUtils;
import rjson.transformer.tojson.FieldBasedTransformer;
import transformers.Context;

public class NullifyDateTransformer extends FieldBasedTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		ToJsonTransformationUtils.printData(null, ToJsonTransformationUtils.printer(context));
	}

	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from != null && from instanceof java.util.Date;
	}
}