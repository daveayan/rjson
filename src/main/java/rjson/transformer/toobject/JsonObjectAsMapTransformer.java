package rjson.transformer.toobject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;

public class JsonObjectAsMapTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		JSONObject jo = (JSONObject) object;
		System.out.println("jsonObjectToObjectMap JSONObject");
		Map<Object, Object> newMap = new HashMap<Object, Object>();
		Iterator<?> iter = jo.getMap().keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			newMap.put(key, rjson.jsonObjectToObjectControl(jo.getMap().get(key)));
		}
		return newMap;
	}
	

	public boolean canConvertToObject(Object object) {
		return (object instanceof JSONObject) & ! ((JSONObject) object).has("class");
	}
}