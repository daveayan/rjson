package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafStringTransformer extends AbstractTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			printData(null, printer);
			return;
		}
		printData(object.toString(), printer);
	}

	public boolean canHandle(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.lang.String) {
			return true;
		}
		return false;
	}
}