package com.daveayan.rjson.transformer.tojson;

import com.daveayan.rjson.transformer.BaseTransformer;
import com.daveayan.transformers.Context;

public class LeafJodaLocalDateTransformer extends BaseTransformer {
	public String transform(Object from, Class< ? > to, String fieldName, Context context) {
		if(cycleDetectedWith(from, context)) return null;
		printData(null, context);
		return null;
	}

	public boolean canTransform(Object from, Class< ? > to, String fieldName, Context context) {
		return from != null && from instanceof org.joda.time.LocalDate;
	}
}