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
import org.junit.Test;

import rjson.domain.IgnoreDateTransformer;
import rjson.domain.ObjectWithFinal;
import rjson.domain.ObjectWithTransient;
import rjson.domain.Person;
import rjson.test.Given;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.tojson.FieldBasedTransformer;

public class ToJsonTest {
	@Test
	public void givenWhenThenTest() {
		Given.objectUnderTestIs(serializer()).when("toJson")
				.isCalledWithParameters(new Object[] { null })
				.assertThatReturnValueIsSameAs("null")
				.assertThatObjectUnderTestIsNotModified()
				.assertThatInputParametersAreNotModified();
	}

	@Test
	public void toJsonNullObject() throws IOException {
		Assert.assertEquals("\"null\"", serializer().toJson(null));
	}

	@Test
	public void toJsonPrimitiveInt() throws IOException {
		Assert.assertEquals("123", serializer().toJson(123));
	}

	@Test
	public void toJsonObjectInt() throws IOException {
		Assert.assertEquals("123", serializer().toJson(new Integer(123)));
	}

	@Test
	public void toJsonPrimitiveFloat() throws IOException {
		Assert.assertEquals("123.45", serializer().toJson(123.45));
	}

	@Test
	public void toJsonObjectFloat() throws IOException {
		Assert.assertEquals("123.45", serializer().toJson(new Float(123.45)));
	}

	@Test
	public void toJsonBigDecimal() throws IOException {
		Assert.assertEquals(
				"123.4560000000000030695446184836328029632568359375",
				serializer().toJson(new BigDecimal(123.456)));
	}

	@Test
	public void toJsonPrimitiveBoolean() throws IOException {
		Assert.assertEquals("true", serializer().toJson(true));
		Assert.assertEquals("false", serializer().toJson(false));
	}

	@Test
	public void toJsonObjectBoolean() throws IOException {
		Assert.assertEquals("true", serializer().toJson(new Boolean(true)));
		Assert.assertEquals("false", serializer().toJson(new Boolean(false)));
	}

	@Test
	public void toJsonString() throws IOException {
		Assert.assertEquals("\"qwerty\"", serializer().toJson("qwerty"));
	}

	@Test
	public void toJsonStringWithSpecialJsonCharacters() throws IOException {
		Assert.assertEquals("\"q\\[w\\]e\\{r\\}ty\"", serializer().toJson(
				"q[w]e{r}ty"));
	}

	@Test
	public void toJsonDate() {
		Date date = new Date(Long.parseLong("1309814968887"));
		Assert.assertEquals("1309814968887", Rjson.newInstance().toJson(date));
	}

	@Test
	public void toJsonEmptyList() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		String actualJson = serializer().toJson(new ArrayList<String>());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonStringList() throws IOException {
		List<String> l = new ArrayList<String>();
		l.add("qwerty");
		l.add("asdfgh");

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		String actualJson = serializer().toJson(l);
		Assert.assertEquals(expectedJson, actualJson);
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
		String actualJson = serializer().toJson(listOfList);
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonEmptyArray() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		String actualJson = serializer().toJson(new String[] {});
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonStringArray() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		String actualJson = serializer().toJson(
				new String[] { "qwerty", "asdfgh" });
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonArrayOfStringArray() throws IOException {
		String[] strArray1 = new String[] { "qwerty", "asdfgh" };
		String[] strArray2 = new String[] { "POIUYT", "LKJHGF" };
		String[][] arrayOfArray = new String[][] { strArray1, strArray2 };

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		String actualJson = serializer().toJson(arrayOfArray);
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonEmptyMap() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt");
		String actualJson = serializer().toJson(new HashMap<String, String>());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonStringStringMap() throws IOException {
		Map<String, String> m = new HashMap<String, String>();
		m.put("key1", "qwerty");
		m.put("key2", "asdfgh");

		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt");
		String actualJson = serializer().toJson(m);
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonAComplexObjectRespectingModifiers() throws IOException {
		Rjson rjson = serializer().andDoNotIgnoreModifiers();
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object-respecting-modifiers.txt");
		String actualJson = rjson.toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonAComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt");
		String actualJson = serializer()
				.toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonAPartialComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/partial-person-object-1.txt");
		String actualJson = serializer().toJson(Person.getPartialInstance1());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonRespectsTransient() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithTransient/default.txt");
		String actualJson = serializer().toJson(new ObjectWithTransient());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonRespectsFinal() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinal/default.txt");
		String actualJson = serializer().toJson(new ObjectWithFinal());
		Assert.assertEquals(expectedJson, actualJson);
	}

	@Test
	public void toJsonMultipleTimesWithSameInstanceOfRjson() {
		Rjson rjson = serializer();
		String firstConversion = rjson.toJson(Person.getFullyLoadedInstance());
		String secondConversion = rjson.toJson(Person.getFullyLoadedInstance());
		String thirdConversion = rjson.toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(firstConversion, secondConversion);
		Assert.assertEquals(secondConversion, thirdConversion);
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
		String actualJson = serializer().with(excludeAddressTransformer)
				.toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(expectedJson, actualJson);
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
		String actualJson = serializer().with(excludeAddressTransformer)
				.toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(expectedJson, actualJson);
	}

	private Rjson serializer() {
		return Rjson.newInstance().with(new IgnoreDateTransformer())
				.andIgnoreModifiers();
	}

	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}