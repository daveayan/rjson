package com.daveayan.rjson.transformer.tojson;

import com.daveayan.rjson.transformer.BaseTransformer;
import com.daveayan.transformers.Context;

public class LeafUUIDTransformer extends BaseTransformer {
	public String transform(Object from, Class<?> to, Context context) {
		if(cycleDetectedWith(from, context)) return null;
			printData(from, context);
		return null;
	}
	
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return from != null && from instanceof java.util.UUID;
	}
}
