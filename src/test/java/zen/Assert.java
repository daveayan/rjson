package zen;

import org.json.JSONException;

import rjson.utils.RjsonUtil;

public class Assert {
	public static void thatEquals(Object expectedObject, Object actualObject) {
		String expectedjson = RjsonUtil.completeSerializer().toJson(expectedObject);
		String actualjson = RjsonUtil.completeSerializer().toJson(actualObject);
		thatJsonEquals(expectedjson, actualjson);
	}
	
	public static void thatJsonEquals(String expectedJson, String actualJson) {
		try {
			Object expectedJsonObject = RjsonUtil.getJsonObject(expectedJson);
			Object returnJsonObject = RjsonUtil.getJsonObject(actualJson);
			org.junit.Assert.assertEquals(expectedJsonObject, returnJsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}
	
	public static void thatJsonEqualsLiterally(String expectedJson, String actualJson) {
		org.junit.Assert.assertEquals(expectedJson, actualJson);
	}
	
	private Assert() {}
}