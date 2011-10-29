package rjson;

import java.lang.reflect.Field;

import rjson.domain.Exclusion;
import transformers.Context;

public class RjsonTestDomainExclusions implements Exclusion {
	public boolean exclude(Field field, Object from, Class< ? > to, Context context) {
		if(from instanceof rjson.domain.RecursiveObject && field.getType().getName().equals("rjson.domain.RecursiveObject")) {
			return true;
		}
		return false;
	}
}