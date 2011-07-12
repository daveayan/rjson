package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;

public class SampleTransformer implements ObjectToJsonTransformer {
	public boolean canConvertToJson(Object object) {
		return false;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		ToJsonTransformationUtils.printData(null, printer);
	}

	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}
}