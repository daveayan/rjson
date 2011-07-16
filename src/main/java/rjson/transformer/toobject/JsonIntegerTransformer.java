package rjson.transformer.toobject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonIntegerTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		return object;
	}
	public boolean canConvertToObject(Object object) {
		return object instanceof Integer;
	}
}