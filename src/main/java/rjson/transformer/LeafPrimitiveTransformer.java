package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafPrimitiveTransformer implements ObjectToJsonTransformer, JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, printer);
			return;
		}
		ToJsonTransformationUtils.printData(object, printer);
	}

	public boolean canConvertToJson(Object object) {
		if (object == null) {
			return true;
		}
		if (object.getClass().isPrimitive()) {
			return true;
		}
		return false;
	}

	public boolean canConvertToObject(Object object) {
		return false;
	}
}