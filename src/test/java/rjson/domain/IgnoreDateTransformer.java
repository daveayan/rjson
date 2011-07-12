package rjson.domain;

import java.lang.reflect.Field;

import rjson.transformer.FieldBasedTransformer;

public class IgnoreDateTransformer extends FieldBasedTransformer {
//	public boolean canConvertToJson(Object object) {
//		if (object instanceof java.util.Date)
//			return true;
//		return false;
//	}
	@Override
	public boolean include(Field field) {
//		if (field.getName().equals("lastUpdate"))
//			return false;
		return true;
	}
//	public void transformToJson(Object object, Printer printer, Rjson rjson) {
////		if (! field.getName().equals("lastUpdate"))
////			super.transformToJson(object, printer, rjson);
//	}
}