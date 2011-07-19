package rjson.transformer;

import rjson.Rjson;

public interface JsonToObjectTransformer extends Transformer {
	public Object transformJsonToObject(Object object, Rjson rjson);

	public boolean canConvertToObject(Object object);
}