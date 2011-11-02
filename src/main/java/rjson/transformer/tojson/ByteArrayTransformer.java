package rjson.transformer.tojson;

import rjson.transformer.ToJsonTransformationUtils;
import transformers.Context;

public class ByteArrayTransformer extends ArrayTransformer {
	public void transformToJson(Object object, Class<?> to, Context context) {
		if(cycleDetectedWith(object, context)) return;
		String str = new String((byte[]) object);
		ToJsonTransformationUtils.printData(str.toString(), ToJsonTransformationUtils.printer(context));
	}
	
	public boolean canConvertToJson(Object object) {
		return canHandle(object);
	}
	
	public static boolean canNotHandle(Object object) {
		return ! canHandle(object);
	}
	
	public static boolean canHandle(Object object) {
		if (object != null) {
			if (object.getClass().isArray() && "byte[]".equals(object.getClass().getCanonicalName())) {
				return true;
			}
		}
		return false;
	}
}