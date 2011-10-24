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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import rjson.domain.ObjectWithEnum;
import rjson.domain.ObjectWithFinalAndStatic;
import rjson.domain.Person;
import rjson.utils.RjsonUtil;
import zen.Given;
import zen.When;

public class ToObjectTest {
	private Given given;
	private When when;

	@Before
	public void setup() {
		given = Given.objectUnderTestIs(Rjson.newInstance());
		when = given.when("toObject");
	}

	@Test
	public void toObjectAnObjectHavingEnum() throws IOException {
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithEnum/object-with-enum.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(new ObjectWithEnum());
	}
	
	@Test
	public void toObjectJsonRepresentingNull() {
		when.methodIsCalledWith("\"null\"").assertThatReturnValueIsSameAs(null);
	}

	@Test
	public void toObjectJsonForAString() {
		when.methodIsCalledWith("\"null1\"").assertThatReturnValueIsSameAs("null1");
	}

	@Test
	public void toObjectJsonForAStringWithWhiteSpaces() {
		when.methodIsCalledWith("\"  qwerty      \"").assertThatReturnValueIsSameAs("  qwerty      ");
	}

	@Test
	public void toObjectJsonForAMap() throws IOException {
		Map<String, String> expectedObject = new HashMap<String, String>();
		expectedObject.put("key1", "qwerty");
		expectedObject.put("key2", "asdfgh");
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt");
		Rjson.newInstance().toObject(actualJson, HashSet.class);
		// when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(expectedObject);
	}

	@Test
	public void toObjectJsonForAEmptyMap() throws IOException {
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(new HashMap<String, String>());
	}

	@Test
	public void toObjectJsonForAList() throws IOException {
		List<String> expectedObject = new ArrayList<String>();
		expectedObject.add("qwerty");
		expectedObject.add("asdfgh");
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(expectedObject);
	}

	@Test
	public void toObjectJsonForAEmptyList() throws IOException {
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(new ArrayList<String>());
	}

	@Test
	public void toObjectJsonForAListWithinList() throws IOException {
		List<List<String>> expectedObject = new ArrayList<List<String>>();

		List<String> l1 = new ArrayList<String>();
		l1.add("qwerty");
		l1.add("asdfgh");

		List<String> l2 = new ArrayList<String>();
		l2.add("POIUYT");
		l2.add("LKJHGF");

		expectedObject.add(l1);
		expectedObject.add(l2);

		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(expectedObject);
	}

	@Test
	public void toObjectJsonForAComplexObject() throws IOException {
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(Person.getFullyLoadedInstance());
	}

	@Test
	public void toObjectJsonForAComplexObjectWithNullValue() throws IOException {
		String actualJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-null-address.txt");
		when.methodIsCalledWith(actualJson).assertThatReturnValueIsSameAs(Person.getFullyLoadedInstanceWithNullAddress());
	}

	@Test
	public void toObjectJsonForAInteger() {
		when.methodIsCalledWith("123").assertThatReturnValueIsSameAs(123);
	}

	@Test
	public void toObjectJsonForAIntegerWithWhiteSpaces() {
		when.methodIsCalledWith("   123     ").assertThatReturnValueIsSameAs(123);
	}

	@Test
	public void toObjectJsonForADouble() {
		when.methodIsCalledWith("123.45").assertThatReturnValueIsSameAs(123.45);
	}

	@Test
	public void toObjectJsonForADoubleWithWhiteSpaces() {
		when.methodIsCalledWith("   123.45      ").assertThatReturnValueIsSameAs(123.45);
	}

	@Test
	public void toObjectJsonForABoolean() {
		when.methodIsCalledWith("true").assertThatReturnValueIsSameAs(true);
	}
}