package rjson.test;

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
		return this;
	}

	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
		this.assertThatObjectUnderTestIsNotModified();
		this.assertThatInputParametersAreNotModified();
		return this;
	}

	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
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