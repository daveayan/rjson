package org.json;


import org.junit.Test;

import com.daveayan.json.JSONException;
import com.daveayan.json.JSONObject;
import com.daveayan.rjson.domain.Person;
import com.daveayan.rjson.utils.RjsonUtil;

import zen.Assert;

public class JSONObject_equals_Test {
	@Test 
	public void verifyStringComparisions() throws JSONException {
		JSONObject jo1 = new JSONObject(RjsonUtil.completeSerializer().toJson(Person.getFullyLoadedInstance()));
		JSONObject jo2 = new JSONObject(RjsonUtil.completeSerializer().toJson(Person.getFullyLoadedInstance()));
		Assert.thatEquals(jo1, jo2);
	}
}