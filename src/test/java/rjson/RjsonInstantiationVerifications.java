package rjson;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import rjson.domain.SampleTransformer;

public class RjsonInstantiationVerifications {
	@Test public void verifyDefaultJsonObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String actualJson = serializer().toJson(Rjson.newInstance());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void verifyDefaultJsonObjectIgnoringModifiers() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_ignoring_modifiers.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().andIgnoreModifiers());
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void verifyDefaultJsonObjectWithCustomTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleTransformer()));
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void verifyDefaultJsonObjectWithMultipleInstancesOfSameCustomTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleTransformer()).and(new SampleTransformer()));
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	@Test public void verifyDefaultJsonObjectWithCustomTransformerAsNull() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(null));
		Assert.assertEquals(expectedJson, actualJson);
	}
	
	private Rjson serializer() {
		return Rjson.newInstance().andIgnoreModifiers();
	}
	
	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}