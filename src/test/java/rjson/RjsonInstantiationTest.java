package rjson;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import rjson.domain.SampleBothTransformer;
import rjson.domain.SampleJsonToObjectTransformer;
import rjson.domain.SampleObjectToJsonTransformer;

public class RjsonInstantiationTest {
	@Test
	public void verifyDefaultJsonObject() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String actualJson = serializer().toJson(Rjson.newInstance());
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectIgnoringModifiers() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_ignoring_modifiers.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().andIgnoreModifiers());
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithCustomObjectToJsonTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_object_to_json_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleObjectToJsonTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithMultipleInstancesOfSameCustomObjectToJsonTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_object_to_json_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleObjectToJsonTransformer()).and(new SampleObjectToJsonTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}
	
	@Test
	public void verifyDefaultJsonObjectWithCustomJsonToObjectTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_json_to_object_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleJsonToObjectTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithMultipleInstancesOfSameCustomJsonToObjectTransformer() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_json_to_object_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleJsonToObjectTransformer()).and(new SampleJsonToObjectTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}
	
	@Test
	public void verifyDefaultJsonObjectATransformerThatHandlesBothTransformers() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_a_single_class_implementing_both_transformers.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleBothTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithCustomTransformerAsNull() throws IOException {
		String expectedJson = fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(null));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	private Rjson serializer() {
		return Rjson.newInstance().andIgnoreModifiers();
	}

	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}