package rjson.utils;

import rjson.transformer.ToJsonTransformationUtils;
import rjson.transformer.tojson.FieldBasedTransformer;
import transformers.Context;

public class NullifyDateTransformer extends FieldBasedTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, ToJsonTransformationUtils.printer(context));
			return;
		}
		ToJsonTransformationUtils.printData(null, ToJsonTransformationUtils.printer(context));
	}

	public boolean canTransform(Object from, Class<?> to, Context context) {
		return canConvertToJson(from);
	}
	
	public boolean canConvertToJson(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.util.Date) {
			return true;
		}
		return false;
	}
}