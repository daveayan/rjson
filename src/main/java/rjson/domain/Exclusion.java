package rjson.domain;

import java.lang.reflect.Field;

import com.daveayan.transformers.Context;

public interface Exclusion {
	public boolean exclude(Field field, Object from, Class<?> to, Context context) ;
}