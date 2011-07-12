package rjson.transformer;

import java.util.Date;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafDateTransformer implements ObjectToJsonTransformer, JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, printer);
			return;
		}
		ToJsonTransformationUtils.printData(((Date) object).getTime(), printer);
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

	public boolean canConvertToObject(Object object) {
		return false;
	}
}