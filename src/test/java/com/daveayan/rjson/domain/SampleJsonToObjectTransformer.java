package com.daveayan.rjson.domain;

import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;

public class SampleJsonToObjectTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, Context context) {
		return false;
	}

	public Object transform(Object from, Class<?> to, Context context) {
		return null;
	}

}