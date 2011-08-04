package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.JsonToObjectTransformer;
import transformers.Context;

public class SampleBothTransformer implements JsonToObjectTransformer {
	public boolean canConvertToJson(Object object) {
		return false;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {

	}

	public boolean canConvertToObject(Object object) {
		return false;
	}

	public Object transformJsonToObject(Object object, Rjson rjson) {
		return null;
	}

	public void transformToJson(Object object, Class<?> to, Context context) {
		// TODO Auto-generated method stub
		
	}
}