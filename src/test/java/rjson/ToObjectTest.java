package rjson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import rjson.domain.Person;

public class ToObjectTest {
	@Test public void toObjectJsonRepresentingNull() {
		Object expectedObject = null;
		Object actualObject = deSerializer().fromJson("\"null\"");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAString() {
		Object expectedObject = "null1";
		Object actualObject = deSerializer().fromJson("\"null1\"");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAStringWithWhiteSpaces() {
		Object expectedObject = "  qwerty      ";
		Object actualObject = deSerializer().fromJson("\"  qwerty      \"");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAMap() throws IOException {
		Map<String, String> expectedObject = new HashMap<String, String>();
		expectedObject.put("key1", "qwerty");
		expectedObject.put("key2", "asdfgh");
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt"));
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAEmptyMap() throws IOException {
		Map<String, String> expectedObject = new HashMap<String, String>();
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt"));
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAList() throws IOException {
		List<String> expectedObject = new ArrayList<String>();
		expectedObject.add("qwerty");
		expectedObject.add("asdfgh");
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-java.util.List/string-list.txt"));
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAEmptyList() throws IOException {
		List<String> expectedObject = new ArrayList<String>();
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt"));
		rjson.test.Assert.thatObjectJsonsMatch(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAListWithinList() throws IOException {
		List <List<String>> expectedObject = new ArrayList<List<String>> ();
		
		List<String> l1 = new ArrayList<String>();
		l1.add("qwerty");
		l1.add("asdfgh");
		
		List<String> l2 = new ArrayList<String>();
		l2.add("POIUYT");
		l2.add("LKJHGF");
		
		expectedObject.add(l1);
		expectedObject.add(l2);
		
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-java.util.List/list-of-string-list.txt"));
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAComplexObject() throws IOException {
		Person expectedObject = Person.getFullyLoadedInstance();
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt"));
		rjson.test.Assert.thatObjectJsonsMatch(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAComplexObjectWithNullValue() throws IOException {
		Person expectedObject = Person.getFullyLoadedInstanceWithNullAddress();
		Object actualObject = deSerializer().fromJson(fileAsString("./src/test/java/DATA-rjson.domain.Person/person-object-with-null-address.txt"));
		rjson.test.Assert.thatObjectJsonsMatch(expectedObject, actualObject);
	}
	
	
	@Test public void toObjectJsonForAInteger() {
		Integer expectedObject = 123;
		Integer actualObject = (Integer) deSerializer().fromJson("123");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForAIntegerWithWhiteSpaces() {
		Integer expectedObject = 123;
		Integer actualObject = (Integer) deSerializer().fromJson("  123      ");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForADouble() {
		Double expectedObject = 123.45;
		Double actualObject = (Double) deSerializer().fromJson("123.45");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForADoubleWithWhiteSpaces() {
		Double expectedObject = 123.45;
		Double actualObject = (Double) deSerializer().fromJson("  123.45      ");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	@Test public void toObjectJsonForABoolean() {
		Boolean expectedObject = true;
		Boolean actualObject = (Boolean) deSerializer().fromJson("true");
		Assert.assertEquals(expectedObject, actualObject);
	}
	
	private Rjson deSerializer() {
		return Rjson.newInstance();
	}
	
	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}