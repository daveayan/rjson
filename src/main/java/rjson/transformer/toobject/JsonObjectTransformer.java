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
				Object jsonField = jo.get(field.getName());
				String jsonFieldContents = jsonField.toString();
				System.out.println("***===>>>" + field.getType().getName() + " : " + " : " + jsonField.getClass().getName() + " : " + jsonFieldContents
						+ " <<<===***");
				Object returnedObject = rjson.jsonObjectToObjectControl(jsonField);
				RjsonUtil.setField(field, objectToBeReturned, returnedObject);
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
		return true;
	}
}