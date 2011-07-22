package rjson.test;

public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		return this;
	}
	
	public Then assertThatObjectUnderTestIsModifiedAs(Object expectedObject) {
		return this;
	}
	
	public Then assertThatInputParametersAreNotModified() {
		return this;
	}
	
	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
		return this;
	}
	
	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
	}
	private When when;
	private Then() {}
}