package rjson.test;

import org.junit.Assert;

import rjson.Rjson;

public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		String objectUnderTestJsonAfterTestExecution = rjson.toJson(when.given().objectUnderTest());
		if (!objectUnderTestJsonAfterTestExecution.equals(when.given().objectUnderTestJsonBeforeTestExecution()))
			throw new AssertionError();
		return this;
	}

	public Then assertThatObjectUnderTestIsModifiedAs(Object expectedObject) {
		return this;
	}

	public Then assertThatInputParametersAreNotModified() {
		Rjson rjson = Rjson.newInstance().and(new NullifyDateTransformer()).andIgnoreModifiers();
		for(int i = 0; i < when.inputParams().size(); i ++) {
			Object object = when.inputParams().get(i);
			String afterExecutionJson = rjson.toJson(object);
			assertEquals(afterExecutionJson, when.inputParamJsons().get(i));			
		}
		return this;
	}

	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
		this.assertThatObjectUnderTestIsNotModified();
		this.assertThatInputParametersAreNotModified();
		assertEquals(expectedObject, returnObject);
		return this;
	}

	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
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