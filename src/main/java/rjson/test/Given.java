package rjson.test;

import rjson.Rjson;

public class Given {
	public static Given objectUnderTestIs(Object objectUnderTest) {
		Given given = new Given();
		given.objectUnderTest = objectUnderTest;
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		given.objectUnderTestJsonBeforeTestExecution = rjson.toJson(objectUnderTest);
		return given;
	}

	public When when(String methodName) {
		When when = When.methodCalledIs(this, methodName);
		return when;
	}

	private Object objectUnderTest;
	private String objectUnderTestJsonBeforeTestExecution;

	public String objectUnderTestJsonBeforeTestExecution() {
		return objectUnderTestJsonBeforeTestExecution;
	}

	public Object objectUnderTest() {
		return objectUnderTest;
	}

	private Given() {
	}
}