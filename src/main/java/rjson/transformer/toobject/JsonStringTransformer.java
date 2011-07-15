package rjson.transformer.toobject;

import org.json.JSONObject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonStringTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		String string = (String) object;
		System.out.println("jsonObjectToObject String");
		if (string != null && string.equals(JSONObject.NULL.toString()))
			return null;
		return string;
	}
	
	public boolean canConvertToObject(Object object) {
		return object instanceof String;
	}
}