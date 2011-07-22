package rjson.test;

public class When {
	public Then isCalledWithParameters(Object[] parameters) {
		return Then.thenAssertChanges(this);
	}

	public Then isCalled() {
		return Then.thenAssertChanges(this);
	}

	public Given getGiven() {
		return given;
	}

	public String getMethodUnderTest() {
		return methodUnderTest;
	}

	public static When methodCalledIs(Given given, String methodName) {
		When when = new When();
		when.given = given;
		when.methodUnderTest = methodName;
		return when;
	}

	private String methodUnderTest;
	private Given given;

	private When() {
	}
}