package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.AbstractTransformer;

public class SampleTransformer extends AbstractTransformer {
	public boolean canHandle(Object object) {
		return false;
	}
	public void transformToJson(Object object, Printer printer, Rjson rjson) {	
		printData(null, printer);
	}
}