package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.Transformer;

public class SampleTransformer implements Transformer {
	public boolean canHandle(Object object) {
		return false;
	}
	public void transform(Object object, Printer printer, Rjson rjson) {	
	}
}