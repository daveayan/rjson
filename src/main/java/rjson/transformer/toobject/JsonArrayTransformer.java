package rjson.transformer.toobject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonArrayTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		JSONArray ja = (JSONArray) object;
		List<Object> newList = new ArrayList<Object>();
		for (Object item : ja.getList()) {
			newList.add(rjson.jsonObjectToObjectControl(item));
		}
		return newList;
	}
	
	public boolean canConvertToObject(Object object) {
		return object instanceof JSONArray;
	}
}