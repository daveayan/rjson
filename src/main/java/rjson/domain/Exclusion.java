package rjson.domain;

import java.lang.reflect.Field;

import transformers.Context;

public interface Exclusion {
	public boolean exclude(Field field, Object from, Class<?> to, Context context) ;
}