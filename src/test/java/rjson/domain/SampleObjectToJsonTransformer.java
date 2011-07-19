package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;

public class SampleObjectToJsonTransformer implements ObjectToJsonTransformer {
	public boolean canConvertToJson(Object object) {
		return false;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		ToJsonTransformationUtils.printData(null, printer);
	}
}