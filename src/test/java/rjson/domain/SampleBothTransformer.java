package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.JsonToObjectTransformer;
import rjson.transformer.ObjectToJsonTransformer;

public class SampleBothTransformer implements ObjectToJsonTransformer, JsonToObjectTransformer {
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
}