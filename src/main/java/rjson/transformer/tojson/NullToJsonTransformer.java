package rjson.transformer.tojson;

import rjson.transformer.BaseTransformer;
import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public class NullToJsonTransformer extends BaseTransformer {
	public String transform(Object from, Class< ? > to, Context context) {
		if(cycleDetectedWith(from, context)) return null;
		ToJsonTransformationUtils.printData(null, ToJsonTransformationUtils.printer(context));
		return null;
	}

	public boolean canTransform(Object from, Class< ? > to, Context context) {
		return from == null;
	}
}