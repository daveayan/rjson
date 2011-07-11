package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;

public class LeafBooleanTransformer extends AbstractTransformer {
	public void transform(Object object, Printer printer, Rjson rjson) {
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
		if (object instanceof java.lang.Boolean) {
			return true;
		}
		return false;
	}
}