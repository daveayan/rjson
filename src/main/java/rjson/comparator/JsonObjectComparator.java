package rjson.comparator;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import transformers.CanTransform;
import transformers.Context;

public class JsonObjectComparator implements CanTransform {
	public boolean canTransform(Object arg0, Class<?> arg1, Context context) {
		return context.get("THIS-JSON-OBJECT") instanceof JSONObject && context.get("OTHER-JSON-OBJECT") instanceof JSONObject;
	}

	public Object transform(Object arg0, Class<?> arg1, Context context) {
		JSONObject thisJson = (JSONObject) context.get("THIS-JSON-OBJECT");
		JSONObject otherJson = (JSONObject) context.get("OTHER-JSON-OBJECT");
		StringBuffer messages = (StringBuffer) context.get("MESSAGES");
		
		if(thisJson == null && otherJson == null) {
    		return false;
    	}
    	Iterator thisIterator = thisJson.keys();
    	while(thisIterator.hasNext()) {
    		String thiskey = (String) thisIterator.next();
    		try {
				Object thisObject = thisJson.get(thiskey);
				Object otherObject = otherJson.get(thiskey);
				if(! thisObject.equals(otherObject)) {
					messages.append("JA NE : " + thisObject + " : " + otherObject);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
    	}
		return null;
	}

}