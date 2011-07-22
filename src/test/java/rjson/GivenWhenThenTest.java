package rjson;

import org.junit.Test;

import rjson.test.Given;
import rjson.test.Then;

public class GivenWhenThenTest {
	@Test public void testStructure() {
		Given given = Given.objectUnderTestIs(Rjson.newInstance());
		Then then = given.when("toJson").isCalled();
		then.assertThatObjectUnderTestIsNotModified();
		then.assertThatInputParametersAreNotModified();
		then.assertThatReturnValueIsSameAs(null);
	}
}