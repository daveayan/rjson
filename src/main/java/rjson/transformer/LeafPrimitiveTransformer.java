package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafPrimitiveTransformer extends AbstractTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			printData(null, printer);
			return;
		}
		printData(object, printer);
	}

	public boolean canHandle(Object object) {
		if (object == null) {
			return true;
		}
		if (object.getClass().isPrimitive()) {
			return true;
		}
		return false;
	}
}