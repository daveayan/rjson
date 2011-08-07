package rjson.test;

import org.junit.Assert;

import rjson.Rjson;
import rjson.utils.NullifyDateTransformer;
import rjson.utils.RjsonUtil;

public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		String objectUnderTestJsonAfterTestExecution = RjsonUtil.completeSerializer().toJson(when.given().objectUnderTest());
		if (!objectUnderTestJsonAfterTestExecution.equals(when.given().objectUnderTestJsonBeforeTestExecution()))
			throw new AssertionError();
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
			assertEquals(afterExecutionJson, when.inputParamJsons().get(i));
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

	public Then assertThatReturnJsonIsSameAsJsonFor(Object expectedObject) {
		assertThatThereAreNoSideEffects();
		assertJsonEquals(expectedObject, returnObject);
		return this;
	}

	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
	}

	private void assertJsonEquals(Object expectedObject, Object returnObject) {
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		assertEquals(RjsonUtil.reformat(rjson.toJson(expectedObject)), RjsonUtil.reformat(rjson.toJson(returnObject)));
	}

	private void assertEquals(Object expectedObject, Object returnObject) {
		Assert.assertEquals(expectedObject, returnObject);
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