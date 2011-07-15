package rjson.transformer.toobject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonDoubleTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		System.out.println("JsonDoubleTransformer");
		return object;
	}
	public boolean canConvertToObject(Object object) {
		return object instanceof Double;
	}
}