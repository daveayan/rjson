package rjson.utils;

import rjson.transformer.ToJsonTransformationUtils;
import rjson.transformer.tojson.FieldBasedTransformer;
import transformers.Context;

public class NullifyDateTransformer extends FieldBasedTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		ToJsonTransformationUtils.printData(null, (StringBuffer) context.get("json_buffer"));
	}

	public boolean canTransform(Object from, Class<?> to, Context context) {
		return canConvertToJson(from);
	}
	
	public boolean canConvertToJson(Object object) {
		return object != null && object instanceof java.util.Date;
	}
}