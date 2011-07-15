package rjson.transformer.toobject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonIntegerTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		System.out.println("jsonObjectToObject Integer");
		return object;
	}
	public boolean canConvertToObject(Object object) {
		return object instanceof Integer;
	}
}