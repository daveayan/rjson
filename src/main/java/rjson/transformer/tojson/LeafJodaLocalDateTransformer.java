package rjson.transformer.tojson;

import rjson.transformer.BaseTransformer;
import com.daveayan.transformers.Context;

public class LeafJodaLocalDateTransformer extends BaseTransformer {
	public String transform(Object from, Class< ? > to, Context context) {
		if(cycleDetectedWith(from, context)) return null;
		printData(null, context);
		return null;
	}

	public boolean canTransform(Object from, Class< ? > to, Context context) {
		return from != null && from instanceof org.joda.time.LocalDate;
	}
}