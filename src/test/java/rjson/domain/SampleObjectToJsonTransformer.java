package rjson.domain;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public class SampleObjectToJsonTransformer implements ObjectToJsonTransformer {
	public boolean canConvertToJson(Object object) {
		return false;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		ToJsonTransformationUtils.printData(null, printer);
	}

	public void transformToJson(Object object, Class<?> to, Context context) {
		// TODO Auto-generated method stub
	}

	public boolean canTransform(Object from, Class<?> to, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object transform(Object from, Class<?> to, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
}