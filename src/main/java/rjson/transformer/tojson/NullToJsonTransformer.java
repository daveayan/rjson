package rjson.transformer.tojson;

import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public class NullToJsonTransformer implements ObjectToJsonTransformer {
	public String transform(Object from, Class< ? > to, Context context) {
		ToJsonTransformationUtils.printData(null, (StringBuffer) context.get("json_buffer"));
		return null;
	}

	public boolean canTransform(Object from, Class< ? > to, Context context) {
		return from == null;
	}
}