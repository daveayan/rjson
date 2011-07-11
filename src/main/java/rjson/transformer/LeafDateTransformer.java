package rjson.transformer;

import java.util.Date;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafDateTransformer extends AbstractTransformer implements JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			printData(null, printer);
			return;
		}
		printData(((Date) object).getTime(), printer);
	}

	public boolean canHandle(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.util.Date) {
			return true;
		}
		return false;
	}
}