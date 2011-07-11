package rjson.transformer;

import rjson.Rjson;

public interface JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson);

	public boolean canHandle(Object object);
}