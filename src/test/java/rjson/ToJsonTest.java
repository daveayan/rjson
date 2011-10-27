/*
 * Copyright (c) 2011 Ayan Dave http://daveayan.com
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * 1) The above copyright notice and this permission notice shall be included without any changes or alterations 
 * in all copies or substantial portions of the Software.
 * 2) This software shall be used for Good, not Evil.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rjson.domain.ObjectWithEnum;
import rjson.domain.ObjectWithFinal;
import rjson.domain.ObjectWithFinalAndStatic;
import rjson.domain.ObjectWithStatic;
import rjson.domain.ObjectWithTransient;
import rjson.domain.Person;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.tojson.FieldBasedTransformer;
import rjson.utils.RjsonUtil;
import transformers.CanTransform;
import transformers.Context;
import zen.Given;
import zen.Then;
import zen.When;

public class ToJsonTest {
	private Given given;
	private When when;

	@Before
	public void setup() {
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer());
		when = given.when("toJson");
	}

	@Test
	public void toJsonNullObject() throws IOException {
		when.methodIsCalledWith((Object) null).assertThatReturnValueIsSameAs("\"null\"");
	}
	
	@Test public void toJsonEmptyObject() {
		when.methodIsCalledWith("").assertThatReturnValueIsSameAs("");
	}

	@Test
	public void toJsonPrimitiveInt() throws IOException {
		when.methodIsCalledWith(123).assertThatReturnValueIsSameAs("123");
	}

	@Test
	public void toJsonObjectInt() throws IOException {
		when.methodIsCalledWith(new Integer(123)).assertThatReturnValueIsSameAs("123");
	}

	@Test
	public void toJsonPrimitiveFloat() throws IOException {
		when.methodIsCalledWith(123.45).assertThatReturnValueIsSameAs("123.45");
	}

	@Test
	public void toJsonObjectFloat() throws IOException {
		when.methodIsCalledWith(new Float(123.45)).assertThatReturnValueIsSameAs("123.45");
	}

	@Test
	public void toJsonBigDecimal() throws IOException {
		when.methodIsCalledWith(new BigDecimal(123.456)).assertThatReturnValueIsSameAs("123.4560000000000030695446184836328029632568359375");
	}

	@Test
	public void toJsonPrimitiveBoolean() throws IOException {
		when.methodIsCalledWith(true).assertThatReturnValueIsSameAs("true");
		when.methodIsCalledWith(false).assertThatReturnValueIsSameAs("false");
	}

	@Test
	public void toJsonObjectBoolean() throws IOException {
		when.methodIsCalledWith(new Boolean(true)).assertThatReturnValueIsSameAs("true");
		when.methodIsCalledWith(new Boolean(false)).assertThatReturnValueIsSameAs("false");
	}

	@Test
	public void toJsonString() throws IOException {
		when.methodIsCalledWith("qwerty").assertThatReturnValueIsSameAs("\"qwerty\"");
	}

	@Test
	public void toJsonStringWithSpecialJsonCharacters() throws IOException {
		when.methodIsCalledWith("q[w]e{r}ty").assertThatReturnValueIsSameAs("\"q\\[w\\]e\\{r\\}ty\"");
	}

	@Test
	public void toJsonDate() {
		Date date = new Date(Long.parseLong("1309814968887"));
		Given given = Given.objectUnderTestIs(Rjson.newInstance());
		given.when("toJson").isCalledWith(date).assertThatReturnValueIsSameAs("1309814968887");
	}

	@Test
	public void toJsonEmptyList() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWith(new ArrayList<String>()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringList() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("qwerty");
		list.add("asdfgh");

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWith(list).assertThatReturnValueIsSameAs(expectedJson);
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

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		when.methodIsCalledWith(listOfList).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonEmptySet() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWith(new HashSet<String>()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringSet() throws IOException {
		Set<String> set = new HashSet<String>();
		set.add("qwerty");
		set.add("asdfgh");
		set.add("qwerty");

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWith(set).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonComplexObjectSet() throws IOException {
		Set<Person> set = new HashSet<Person>();
		set.add(Person.getFullyLoadedInstance());

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.Set/set-of-person-object.txt");
		when.methodIsCalledWith(set).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonEmptyArray() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWith((Object) new String[] {}).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringArray() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWith((Object) new String[] { "qwerty", "asdfgh" }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonByteArray() throws IOException {
		when.methodIsCalledWith("Hello World".getBytes()).assertThatReturnValueIsSameAs("Hello World");
	}

	@Test
	public void toJsonArrayOfStringArray() throws IOException {
		String[] strArray1 = new String[] { "qwerty", "asdfgh" };
		String[] strArray2 = new String[] { "POIUYT", "LKJHGF" };
		String[][] arrayOfArray = new String[][] { strArray1, strArray2 };

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		when.methodIsCalledWith((Object) arrayOfArray).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonEmptyMap() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt");
		when.methodIsCalledWith(new HashMap<String, String>()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonStringStringMap() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "qwerty");
		map.put("key2", "asdfgh");

		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt");
		when.methodIsCalledWith(map).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAComplexObjectRespectingModifiers() throws IOException {
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().andDoNotRecordAllModifiers());
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object-respecting-modifiers.txt");
		given.when("toJson").isCalledWith(new Object[] { Person.getFullyLoadedInstance() }).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAComplexObject() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt");
		when.methodIsCalledWith(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonAPartialComplexObject() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/partial-person-object-1.txt");
		when.methodIsCalledWith(Person.getPartialInstance1()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonRespectsTransient() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithTransient/default.txt");
		when.methodIsCalledWith(new ObjectWithTransient()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonWhenFinalIsNotRecorded() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinal/with-final-not-recorded.txt");
		when.methodIsCalledWith(new ObjectWithFinal()).assertThatReturnValueIsSameAs(expectedJson);
	}
	
	@Test
	public void toJsonWhenFinalIsRecorded() throws IOException {
		Given given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().andRecordFinal());
		When when = given.when("toJson");
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinal/with-final-recorded.txt");
		when.methodIsCalledWith(new ObjectWithFinal()).assertThatReturnValueIsExactlySameAs(expectedJson);
	}
	
	@Test
	public void toJsonWhenStaticIsNotRecorded() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithStatic/with-static-not-recorded.txt");
		when.methodIsCalledWith(new ObjectWithStatic()).assertThatReturnValueIsSameAs(expectedJson);
	}
	
	@Test
	public void toJsonWhenStaticIsRecorded() throws IOException {
		Given given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().andRecordStatic());
		When when = given.when("toJson");
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithStatic/with-static-recorded.txt");
		when.methodIsCalledWith(new ObjectWithStatic()).assertThatReturnValueIsExactlySameAs(expectedJson);
	}
	
	@Test
	public void toJsonWhenStaticAndFinalIsRecorded() throws IOException {
		Given given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().andRecordStatic().andRecordFinal());
		When when = given.when("toJson");
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinalAndStatic/with-static-and-final-recorded.txt");
		when.methodIsCalledWith(new ObjectWithFinalAndStatic()).assertThatReturnValueIsExactlySameAs(expectedJson);
	}
	
	@Test
	public void toJsonWhenStaticOrFinalNothingIsRecorded() throws IOException {
		Given given = Given.objectUnderTestIs(RjsonUtil.completeSerializer());
		When when = given.when("toJson");
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinalAndStatic/with-nothing-recorded.txt");
		when.methodIsCalledWith(new ObjectWithFinalAndStatic()).assertThatReturnValueIsExactlySameAs(expectedJson);
	}
	
	@Test
	public void toJsonAnObjectWithEnum() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithEnum/object-with-enum.txt");
		when.methodIsCalledWith(new ObjectWithEnum()).assertThatReturnValueIsExactlySameAs(expectedJson);
	}

	@Test
	public void toJsonMultipleTimesWithSameInstanceOfRjson() {
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer());
		when = given.when("toJson");
		Then firstConversion = when.methodIsCalledWith(Person.getFullyLoadedInstance());
		Then secondConversion = when.methodIsCalledWith(Person.getFullyLoadedInstance());
		Then thirdConversion = when.methodIsCalledWith(Person.getFullyLoadedInstance());

		firstConversion.assertThatReturnValueIsSameAs(secondConversion.getReturnObject().toString());
		secondConversion.assertThatReturnValueIsSameAs(thirdConversion.getReturnObject().toString());
		firstConversion.assertThatReturnValueIsSameAs(thirdConversion.getReturnObject().toString());
	}

	@Test
	public void toJsonPersonObjectNotIncludingAddress() throws IOException {
		CanTransform excludeAddressTransformer = new FieldBasedTransformer() {
			@Override
			public boolean canTransform(Object from, Class<?> to, Context context) {
				if (from instanceof rjson.domain.Person)
					return true;
				return false;
			}

			public boolean include(Field field) {
				if (field.getName().equals("addresses"))
					return false;
				return true;
			}
		};
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-addresses-excluded.txt");
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().with((ObjectToJsonTransformer) excludeAddressTransformer));
		given.when("toJson").isCalledWith(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}

	@Test
	public void toJsonPersonObjectExcludingAddress() throws IOException {
		CanTransform excludeAddressTransformer = new FieldBasedTransformer() {
			@Override
			public boolean canTransform(Object from, Class<?> to, Context context) {
				if (from instanceof rjson.domain.Person)
					return true;
				return false;
			}

			public boolean exclude(Field field) {
				if (field.getName().equals("addresses"))
					return true;
				return false;
			}
		};
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-addresses-excluded.txt");
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer().with((ObjectToJsonTransformer) excludeAddressTransformer));
		given.when("toJson").isCalledWith(Person.getFullyLoadedInstance()).assertThatReturnValueIsSameAs(expectedJson);
	}
}