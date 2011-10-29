package zen;

import rjson.Rjson;
import rjson.utils.RjsonUtil;

public class Given {	
	
	public Given rjsonInstanceIs(Rjson rjson) {
		this.rjson_instance = rjson;
		return this;
	}
	
	public Given classUnderTestIs(Class<?> classUnderTest) {
		this.classUnderTest = classUnderTest;
		return this;
	}

	public Given objectUnderTestIs(Object objectUnderTest) {
		this.objectUnderTest = objectUnderTest;
		this.classUnderTest = objectUnderTest.getClass();
		this.objectUnderTestJsonBeforeTestExecution = rjson_instance.toJson(objectUnderTest);
		return this;
	}

	public When when(String methodName) {
		When when = When.methodCalledIs(this, methodName);
		return when;
	}

	private Class<?> classUnderTest;
	private Object objectUnderTest;
	private String objectUnderTestJsonBeforeTestExecution;
	private Rjson rjson_instance = RjsonUtil.completeSerializer();

	public String objectUnderTestJsonBeforeTestExecution() {
		return objectUnderTestJsonBeforeTestExecution;
	}

	public Object objectUnderTest() {
		return objectUnderTest;
	}

	public Class<?> classUnderTest() {
		return classUnderTest;
	}
	
	public Rjson rjsonInstance() {
		return rjson_instance;
	}
	
	public Given and() {
		return this;
	}
	
	public static Given that() {
		return new Given();
	}

	private Given() {
	}
}