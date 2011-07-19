package rjson.domain;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class SampleJsonToObjectTransformer implements JsonToObjectTransformer {
	public boolean canConvertToObject(Object object) {
		return false;
	}

	public Object transformJsonToObject(Object object, Rjson rjson) {
		return null;
	}

}