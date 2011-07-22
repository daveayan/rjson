package rjson.test;

public class Given {
	public static Given objectUnderTestIs(Object objectUnderTest) {
		Given given = new Given();
		given.objectUnderTest = objectUnderTest;
		return given;
	}
	
	public When when(String methodName) {
		When when = When.methodCalledIs(this, methodName);
		return when;
	}
	
	public Object getObjectUnderTest() {
		return objectUnderTest;
	}

	private Object objectUnderTest;
	
	private Given() {}
}