package zen;


public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		String objectUnderTestJsonAfterTestExecution = when.given().rjsonInstance().toJson(when.given().objectUnderTest());
		Assert.thatJsonEqualsLiterally(when.given().objectUnderTestJsonBeforeTestExecution(), objectUnderTestJsonAfterTestExecution);
		return this;
	}

	public Then assertThatObjectUnderTestIsModifiedAs(Object expectedObject) {
		return this;
	}

	public Then assertThatInputParametersAreNotModified() {
		for (int i = 0; i < when.inputParams().size(); i++) {
			Object object = when.inputParams().get(i);
			String afterExecutionJson = when.given().rjsonInstance().toJson(object);
			Assert.thatJsonEqualsLiterally(afterExecutionJson, when.inputParamJsons().get(i));
		}
		return this;
	}

	public Then assertThatThereAreNoSideEffects() {
		this.assertThatObjectUnderTestIsNotModified();
		this.assertThatInputParametersAreNotModified();
		return this;
	}

	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
//		assertThatThereAreNoSideEffects();
		Assert.thatEquals(expectedObject, returnObject);
		return this;
	}
	
	public Then assertThatReturnValueIsExactlySameAs(Object expectedObject) {
		assertThatThereAreNoSideEffects();
		Assert.thatEquals(when.given().rjsonInstance(), expectedObject, returnObject);
		Assert.thatEquals(when.given().rjsonInstance(), returnObject, expectedObject);
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