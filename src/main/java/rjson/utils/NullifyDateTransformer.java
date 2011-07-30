package rjson.utils;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.ToJsonTransformationUtils;
import rjson.transformer.tojson.FieldBasedTransformer;

public class NullifyDateTransformer extends FieldBasedTransformer {
	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, printer);
			return;
		}
		ToJsonTransformationUtils.printData(null, printer);
	}

	public boolean canConvertToJson(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.util.Date) {
			return true;
		}
		return false;
	}
}