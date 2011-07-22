package rjson.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rjson.utils.ReflectionUtils;

public class When {
	public Then isCalledWithParameters(Object[] parameters) {
		try {
			Method method = ReflectionUtils.getMethodFor(given.objectUnderTest(), this.methodUnderTest, parameters);
			Object returnValue = method.invoke(given.objectUnderTest(), parameters);
			Then then = Then.thenAssertChanges(this);
			then.setReturnObject(returnValue);
			return then;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Then.thenAssertChanges(this);
	}

	public Then isCalled() {
		return Then.thenAssertChanges(this);
	}

	public Given given() {
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