package com.daveayan.rjson;

import java.lang.reflect.Field;

import com.daveayan.rjson.domain.Exclusion;
import com.daveayan.transformers.Context;

public class RjsonTestDomainExclusions implements Exclusion {
	public boolean exclude(Field field, Object from, Class< ? > to, Context context) {
		if(from instanceof com.daveayan.rjson.domain.RecursiveObject && field.getType().getName().equals("com.daveayan.rjson.domain.RecursiveObject")) {
			return true;
		}
		return false;
	}
}