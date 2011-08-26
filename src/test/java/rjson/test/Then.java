package rjson.test;

import org.json.JSONException;
import org.junit.Assert;

import rjson.Rjson;
import rjson.utils.NullifyDateTransformer;
import rjson.utils.RjsonUtil;

public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		String objectUnderTestJsonAfterTestExecution = RjsonUtil.completeSerializer().toJson(when.given().objectUnderTest());
		assertJsonEqualsLiterally(when.given().objectUnderTestJsonBeforeTestExecution(), objectUnderTestJsonAfterTestExecution);
		return this;
	}

	public Then assertThatObjectUnderTestIsModifiedAs(Object expectedObject) {
		return this;
	}

	public Then assertThatInputParametersAreNotModified() {
		Rjson rjson = Rjson.newInstance().and(new NullifyDateTransformer()).andIgnoreModifiers();
		for (int i = 0; i < when.inputParams().size(); i++) {
			Object object = when.inputParams().get(i);
			String afterExecutionJson = rjson.toJson(object);
			assertJsonEqualsLiterally(afterExecutionJson, when.inputParamJsons().get(i));
		}
		return this;
	}

	public Then assertThatThereAreNoSideEffects() {
		this.assertThatObjectUnderTestIsNotModified();
		this.assertThatInputParametersAreNotModified();
		return this;
	}

	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
		assertThatThereAreNoSideEffects();
		assertEquals(expectedObject, returnObject);
		return this;
	}
	
	public Then assertThatReturnJsonIsSameAs(String expectedJson) {
		assertThatThereAreNoSideEffects();
		assertEquals(RjsonUtil.reformat(expectedJson), RjsonUtil.reformat(returnObject.toString()));
		return this;
	}

	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
	}

	private void assertJsonEqualsLiterally(String expectedJson, String actualJson) {
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	public void assertJsonEquals(String expectedJson, String actualJson) {
		try {
			Object expectedJsonObject = RjsonUtil.getJsonObject(expectedJson);
			Object returnJsonObject = RjsonUtil.getJsonObject(actualJson);
			Assert.assertEquals(expectedJsonObject, returnJsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void assertEquals(Object expectedObject, Object actualObject) {
		String expectedjson = RjsonUtil.completeSerializer().toJson(expectedObject);
		String actualjson = RjsonUtil.completeSerializer().toJson(returnObject);
		assertJsonEquals(expectedjson, actualjson);
	}

	private When when;
	private Object returnObject;

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	private Then() {
	}
}