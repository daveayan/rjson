package com.daveayan.rjson.transformer.toobject;

import java.util.Calendar;
import java.util.Date;

import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.transformers.Context;

public class DateMillisToDateTransformer implements JsonToObjectTransformer {
	public boolean canTransform(Object from, Class<?> to, String fieldName, Context context) {
		if(from == null) { return false; }
		return from instanceof Long && ReflectionUtils.classIsOfEitherType(to, Date.class);
	}

	public Object transform(Object from, Class<?> to, String fieldName, Context context) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(from.toString()));
		return calendar.getTime();
	}
}
