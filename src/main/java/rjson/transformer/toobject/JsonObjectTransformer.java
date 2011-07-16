package rjson.transformer.toobject;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import rjson.Rjson;
import rjson.transformer.JsonToObjectTransformer;
import rjson.utils.RjsonUtil;

public class JsonObjectTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		JSONObject jo = (JSONObject) object;
		try {
			Object objectToBeReturned = RjsonUtil.objectFor(jo.getString("class"));
			List<Field> fields = RjsonUtil.getAllFieldsIn(objectToBeReturned);
			Iterator<Field> iter = fields.iterator();
			while (iter.hasNext()) {
				Field field = iter.next();
				RjsonUtil.makeAccessible(field);
				if (jo.has(field.getName())) {
					Object jsonField = jo.get(field.getName());
					Object returnObject = rjson.jsonObjectToObjectControl(jsonField);
					RjsonUtil.setField(field, objectToBeReturned, returnObject);
				}
			}
			return objectToBeReturned;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean canConvertToObject(Object object) {
		return (object instanceof JSONObject) & ((JSONObject) object).has("class");
	}
}