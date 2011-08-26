package rjson.comparator;

import org.json.JSONArray;

import transformers.CanTransform;
import transformers.Context;

public class JsonArrayComparator implements CanTransform {
	public boolean canTransform(Object arg0, Class<?> arg1, Context context) {
		return context.get("THIS-JSON-OBJECT") instanceof JSONArray && context.get("OTHER-JSON-OBJECT") instanceof JSONArray;
	}
	public Object transform(Object arg0, Class<?> arg1, Context context) {
		JSONArray thisJson = (JSONArray) context.get("THIS-JSON-OBJECT");
		JSONArray otherJson = (JSONArray) context.get("OTHER-JSON-OBJECT");
		StringBuffer messages = (StringBuffer) context.get("MESSAGES");
		
		if(otherJson == null) {
    		return null;
    	}
    	if(thisJson.getList() == null && otherJson.getList() == null)
    		return null;
    	if(thisJson.getList() != null && otherJson.getList() == null) {
    		messages.append("JA NE null: " + thisJson + " : " + otherJson);
    		return null;
    	}
    	if(thisJson.getList() == null && otherJson.getList() != null) {
    		messages.append("JA NE null: " + thisJson + " : " + otherJson);
    		return null;
    	}
    	if(thisJson.getList().size() != otherJson.getList().size()) {
    		messages.append("JA NE Size: " + thisJson + " : " + otherJson);
    		return null;
    	}
    	for(int i = 0; i < thisJson.getList().size(); i++) {
    		Object thisObject = thisJson.getList().get(i);
    		Object otherObject = otherJson.getList().get(i);
    		if(!thisObject.equals(otherObject)) {
    			messages.append("JA NE : " + thisObject + " : " + otherObject);
    		}
    	}
    	return null;
	}
}