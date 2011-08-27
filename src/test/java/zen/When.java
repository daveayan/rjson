package zen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mirage.ReflectionUtils;
import rjson.Rjson;
import rjson.utils.RjsonUtil;

public class When {
	public Then methodIsCalledWith(Object... parameters) {
		return this.isCalledWith(parameters);
	}
	
	public Then isCalledWith(Object... parameters) {
		if(parameters == null) {
			parameters = new Object[] {null};
		}
		recordParameters(parameters);
		try {
			Method method = ReflectionUtils.getMethodFor(given.classUnderTest(), this.methodUnderTest, parameters);
			Object returnValue = method.invoke(given.objectUnderTest(), parameters);
			Then then = Then.thenAssertChanges(this);
			then.setReturnObject(returnValue);
			return then;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new AssertionError(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new AssertionError(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new AssertionError(e);
		}
	}
	
	private void recordParameters(Object[] parameters) {
		if(parameters == null) return;
		Rjson rjson = RjsonUtil.completeSerializer();
		for(Object parameter: parameters) {
			inputParams.add(parameter);
			inputParamJsons.add(rjson.toJson(parameter));
		}
	}

	public List<String> inputParamJsons() {
		return inputParamJsons;
	}

	public List<Object> inputParams() {
		return inputParams;
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
	private List<String> inputParamJsons = new ArrayList<String>();
	private List<Object> inputParams = new ArrayList<Object>();

	private When() {
	}
}