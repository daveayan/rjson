package com.daveayan.rjson.domain;

import com.daveayan.rjson.transformer.BaseTransformer;

import com.daveayan.transformers.Context;

public class SampleObjectToJsonTransformer extends BaseTransformer {
	public boolean canConvertToJson(Object object) {
		return false;
	}

//	public void transformToJson(Object object, Printer printer, Rjson rjson) {
//		ToJsonTransformationUtils.printData(null, printer);
//	}

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