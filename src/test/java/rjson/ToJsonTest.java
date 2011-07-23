/*
 * Copyright (c) 2011 Ayan Dave http://www.linkedin.com/in/daveayan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * 1) The above copyright notice and this permission notice shall be included without any changes or alterations 
 * in all copies or substantial portions of the Software.
 * 2) The copyright notice part of the org.json package and its classes shall be honored.
 * 3) This software shall be used for Good, not Evil.
 * portions of the Software.

 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rjson.domain.IgnoreDateTransformer;
import rjson.domain.ObjectWithFinal;
import rjson.domain.ObjectWithTransient;
import rjson.domain.Person;
import rjson.test.Given;
import rjson.test.Then;
import rjson.test.When;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.tojson.FieldBasedTransformer;

public class ToJsonTest {
	private Given given;
	private When when;
	private Then then;

	@Before
	public void setup() {
		given = Given.objectUnderTestIs(Rjson.newInstance().with(new IgnoreDateTransformer()).andIgnoreModifiers());
		when = given.when("toJson");
		then = null;
	}

	@Test
	public void toJsonNullObject() throws IOException {
		Assert.assertEquals("\"null\"", serializer().toJson(null));
	}

	@Test
	public void toJsonPrimitiveInt() throws IOException {
		when.methodIsCalledWithParameters(123).assertThatReturnValueIsSameAs("123");
	}

	@Test
	public void toJsonObjectInt() throws IOException {
		when.methodIsCalledWithParameters(new Integer(123)).assertThatReturnValueIsSameAs("123");
	}

	@Test
	public void toJsonPrimitiveFloat() throws IOException {
		when.methodIsCalledWithParameters(123.45).assertThatReturnValueIsSameAs("123.45");
	}

	@Test
	public void toJsonObjectFloat() throws IOException {
		when.methodIsCalledWithParameters(new Float(123.45)).assertThatReturnValueIsSameAs("123.45");
	}

	@Test
	public void toJsonBigDecimal() throws IOException {
		when.methodIsCalledWithParameters(new BigDecimal(123.456)).assertThatReturnValueIsSameAs("123.4560000000000030695446184836328029632568359375");
	}

	@Test
	public void toJsonPrimitiveBoolean() throws IOException {
		when.methodIsCalledWithParameters(true).assertThatReturnValueIsSameAs("true");
		when.methodIsCalledWithParameters(false).assertThatReturnValueIsSameAs("false");
	}

	@Test
	public void toJsonObjectBoolean() throws IOException {
		when.methodIsCalledWithParameters(new Boolean(true)).assertThatReturnValueIsSameAs("true");
		when.methodIsCalledWithParameters(new Boolean(false)).assertThatReturnValueIsSameAs("false");
	}

	@Test
	public void toJsonString() throws IOException {
		when.methodIsCalledWithParameters("qwerty").assertThatReturnValueIsSameAs("\"qwerty\"");
	}

	@Test
	public void toJsonStringWithSpecialJsonCharacters() throws IOException {
		when.methodIsCalledWithParameters("q[w]e{r}ty").assertThatReturnValueIsSameAs("\"q\\[w\\]e\\{r\\}ty\"");
	}

	@Test
	public void toJsonDate() {
		Date date = new Date(Long.parseLong("1309814968887"));
		Given given = Given.objectUnderTestIs(Rjson.newInstance());
		given.when("toJson").isCalledWithParameters(date).assertThatReturnValueIsSameAs("1309814968887");
	}

	@Test
	public void toJsonEmptyList() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWithParameters(new ArrayList<String>()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringList() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("qwerty");
		list.add("asdfgh");

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWithParameters(list).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringListWithinList() throws IOException {
		List<List<String>> listOfList = new ArrayList<List<String>>();

		List<String> l1 = new ArrayList<String>();
		l1.add("qwerty");
		l1.add("asdfgh");

		List<String> l2 = new ArrayList<String>();
		l2.add("POIUYT");
		l2.add("LKJHGF");

		listOfList.add(l1);
		listOfList.add(l2);

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		when.methodIsCalledWithParameters(listOfList).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonEmptyArray() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWithParameters(new Object[] { new String[] {} }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringArray() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWithParameters(new Object[] { new String[] { "qwerty", "asdfgh" } }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonArrayOfStringArray() throws IOException {
		String[] strArray1 = new String[] { "qwerty", "asdfgh" };
		String[] strArray2 = new String[] { "POIUYT", "LKJHGF" };
		String[][] arrayOfArray = new String[][] { strArray1, strArray2 };

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		when.methodIsCalledWithParameters(new Object[] { arrayOfArray }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonEmptyMap() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt");
		when.methodIsCalledWithParameters(new HashMap<String, String>()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringStringMap() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "qwerty");
		map.put("key2", "asdfgh");

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt");		
		when.methodIsCalledWithParameters(map).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAComplexObjectRespectingModifiers() throws IOException {
		given = Given.objectUnderTestIs(serializer().andDoNotIgnoreModifiers());
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object-respecting-modifiers.txt");
		given.when("toJson").isCalledWithParameters(new Object[] { Person.getFullyLoadedInstance() }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt");
		when.methodIsCalledWithParameters(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAPartialComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/partial-person-object-1.txt");
		when.methodIsCalledWithParameters(Person.getPartialInstance1()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonRespectsTransient() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithTransient/default.txt");
		when.methodIsCalledWithParameters(new ObjectWithTransient()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonRespectsFinal() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinal/default.txt");
		when.methodIsCalledWithParameters(new ObjectWithFinal()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonMultipleTimesWithSameInstanceOfRjson() {
		given = Given.objectUnderTestIs(serializer());
		when = given.when("toJson");
		Then firstConversion = when.methodIsCalledWithParameters(Person.getFullyLoadedInstance());
		Then secondConversion = when.methodIsCalledWithParameters(Person.getFullyLoadedInstance());
		Then thirdConversion = when.methodIsCalledWithParameters(Person.getFullyLoadedInstance());
		
		firstConversion.assertThatReturnValueIsSameAs(secondConversion.getReturnObject());
		secondConversion.assertThatReturnValueIsSameAs(thirdConversion.getReturnObject());
		firstConversion.assertThatReturnValueIsSameAs(thirdConversion.getReturnObject());
	}

	@Test
	public void toJsonPersonObjectNotIncludingAddress() throws IOException {
		ObjectToJsonTransformer excludeAddressTransformer = new FieldBasedTransformer() {
			@Override
			public boolean canConvertToJson(Object object) {
				if (object instanceof rjson.domain.Person)
					return true;
				return false;
			}

			public boolean include(Field field) {
				if (field.getName().equals("addresses"))
					return false;
				return true;
			}
		};
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-addresses-excluded.txt");
		given = Given.objectUnderTestIs(serializer().with(excludeAddressTransformer));
		given.when("toJson").isCalledWithParameters(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonPersonObjectExcludingAddress() throws IOException {
		ObjectToJsonTransformer excludeAddressTransformer = new FieldBasedTransformer() {
			@Override
			public boolean canConvertToJson(Object object) {
				if (object instanceof rjson.domain.Person)
					return true;
				return false;
			}

			public boolean exclude(Field field) {
				if (field.getName().equals("addresses"))
					return true;
				return false;
			}
		};
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-addresses-excluded.txt");
		given = Given.objectUnderTestIs(serializer().with(excludeAddressTransformer));
		given.when("toJson").isCalledWithParameters(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}

	private Rjson serializer() {
		return Rjson.newInstance().with(new IgnoreDateTransformer()).andIgnoreModifiers();
	}

	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}