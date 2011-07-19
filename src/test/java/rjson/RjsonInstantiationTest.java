/*
 * Copyright (c) 2011 Ayan Dave http://www.linkedin.com/in/daveayan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 * 
 * The copyright notice part of the org.json package and its classes shall be honored.
 * This software shall be used for Good, not Evil.
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