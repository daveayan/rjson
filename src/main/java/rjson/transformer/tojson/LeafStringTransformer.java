package rjson.transformer.tojson;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.JsonToObjectTransformer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;

public class LeafStringTransformer implements ObjectToJsonTransformer, JsonToObjectTransformer {
	public Object transformJsonToObject(Object object, Rjson rjson) {
		// TODO Auto-generated method stub
		return null;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		if (object == null) {
			ToJsonTransformationUtils.printData(null, printer);
			return;
		}
		ToJsonTransformationUtils.printData(object.toString(), printer);
	}

	public boolean canConvertToJson(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof java.lang.String) {
			return true;
		}
		return false;
	}

	public boolean canConvertToObject(Object object) {
		return false;
	}
}