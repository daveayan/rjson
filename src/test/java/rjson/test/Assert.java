package rjson.test;

import org.apache.commons.lang.StringUtils;

import rjson.Rjson;
import rjson.utils.RjsonUtil;

public class Assert {
	public static void thatObjectJsonsMatch(Object expected, Object actual) {
		Rjson rjson = RjsonUtil.completeSerializer();
		thatObjectJsonsMatch(rjson.toJson(expected), rjson.toJson(actual));
	}

	public static void thatObjectJsonsMatch(String expected, String actual) {
		if(! StringUtils.equals(expected, actual)) {
			String difference1 = StringUtils.difference(expected, actual);
			String difference2 = StringUtils.difference(actual, expected);
			
			System.out.println(difference1);
			System.out.println("\n\n");
			System.out.println(difference2);
			
			throw new AssertionError("Difference1: " + difference1 + "\nExpected: " + expected + "\nActual: " + actual);
		}
	}
	
	private Assert() {}
}