package rjson.transformer.tojson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import mirage.ReflectionUtils;

import rjson.transformer.ToJsonTransformationUtils;
import transformers.AllowTransformation;
import transformers.Context;

public class RjsonAllowTransformationChecker implements AllowTransformation {
	public boolean allowTransformation(Object from, Class< ? > to, Context context) {
		if (from != null) {
			if (Modifier.isTransient(from.getClass().getModifiers())) {
				return false;
			}
		}
		return true;
	}

	public boolean allowTransformation(Field field, Context context) {
		if (field != null) {
			if (Modifier.isTransient(field.getModifiers())) {
				return false;
			}
			if (ToJsonTransformationUtils.rjson(context).doNotRecordFinal() && Modifier.isFinal(field.getModifiers())) {
				return false;
			}
			if (ToJsonTransformationUtils.rjson(context).doNotRecordStatic() && Modifier.isStatic(field.getModifiers())) {
				return false;
			}
			if (ToJsonTransformationUtils.rjson(context).recordAllModifiers() || ReflectionUtils.isAccessible(field)) {
				return true;
			}
		}
		return true;
	}
}