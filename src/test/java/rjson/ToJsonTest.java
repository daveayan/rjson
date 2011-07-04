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

import rjson.domain.ObjectWithFinal;
import rjson.domain.ObjectWithTransient;
import rjson.domain.Person;
import rjson.printer.Printer;
import rjson.transformer.Transformer;

public class ToJsonTest {	
	@Test public void toJsonNullObject() throws IOException {
		Assert.assertEquals("\"null\"", serializer().toJson(null));
	}
	
	@Test public void toJsonPrimitiveInt() throws IOException {
		Assert.assertEquals("123", serializer().toJson(123));
	}
	
	@Test public void toJsonObjectInt() throws IOException {
		Assert.assertEquals("123", serializer().toJson(new Integer(123)));
	}
	
	@Test public void toJsonPrimitiveFloat() throws IOException {
		Assert.assertEquals("123.45", serializer().toJson(123.45));
	}
	
	@Test public void toJsonObjectFloat() throws IOException {
		Assert.assertEquals("123.45", serializer().toJson(new Float(123.45)));
	}
	
	@Test public void toJsonPrimitiveBoolean() throws IOException {
		Assert.assertEquals("true", serializer().toJson(true));
		Assert.assertEquals("false", serializer().toJson(false));
	}
	
	@Test public void toJsonObjectBoolean() throws IOException {
		Assert.assertEquals("true", serializer().toJson(new Boolean(true)));
		Assert.assertEquals("false", serializer().toJson(new Boolean(false)));
	}
	
	@Test public void toJsonString() throws IOException {
		Assert.assertEquals("\"qwerty\"", serializer().toJson("qwerty"));
	}
	
	@Test public void toJsonStringWithSpecialJsonCharacters() throws IOException {
		Assert.assertEquals("\"q\\[w\\]e\\{r\\}ty\"", serializer().toJson("q[w]e{r}ty"));
	}
	
	@Test public void toJsonEmptyList() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/empty-list.txt");
		String actualJson = serializer().toJson(new ArrayList<String>());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonStringList() throws IOException {
		List<String> l = new ArrayList<String>();
		l.add("qwerty");
		l.add("asdfgh");
		
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.List/string-list.txt");
		String actualJson = serializer().toJson(l);
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonEmptyMap() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/empty-map.txt");
		String actualJson = serializer().toJson(new HashMap<String, String>());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonStringStringMap() throws IOException {
		Map<String, String> m = new HashMap<String, String>();
		m.put("key1", "qwerty");
		m.put("key2", "asdfgh");
		
		String expectedJson = fileAsString("./src/test/java/DATA-java.util.Map/string-string-map.txt");
		String actualJson = serializer().toJson(m);
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonAComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object.txt");
		String actualJson = serializer().toJson(Person.getFullyLoadedInstance());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonAPartialComplexObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.Person/partial-person-object-1.txt");
		String actualJson = serializer().toJson(Person.getPartialInstance1());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonRespectsTransient() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithTransient/default.txt");
		String actualJson = serializer().toJson(new ObjectWithTransient());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void toJsonRespectsFinal() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.domain.ObjectWithFinal/default.txt");
		String actualJson = serializer().toJson(new ObjectWithFinal());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	private Rjson serializer() {
		Transformer ignoreDateTransformer = new Transformer() {
			public boolean canHandle(Object object) {
				if(object instanceof java.util.Date)
					return true;
				return false;
			}
			public void transform(Object object, Printer printer, Rjson rjson) {
				return;
			}
		};
		return Rjson.newInstance().andIgnoreModifiers().and(ignoreDateTransformer);
	}
	
	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}