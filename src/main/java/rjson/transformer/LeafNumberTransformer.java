package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafNumberTransformer extends AbstractTransformer implements JsonToObjectTransformer {
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

	public boolean canConvertToJson(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.lang.Number) {
			return true;
		}
		return false;
	}

	public boolean canConvertToObject(Object object) {
		return false;
	}
}