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

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import rjson.domain.SampleJsonToObjectTransformer;
import rjson.domain.SampleObjectToJsonTransformer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.utils.RjsonUtil;

public class RjsonInstantiationTest {
	
	@Test
	public void trial() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String json1 = RjsonUtil.reformat(expectedJson);
		String json2 = RjsonUtil.reformat(expectedJson);
		String json3 = RjsonUtil.reformat(expectedJson);
		
		String json4 = RjsonUtil.reformat(json1);
		String json5 = RjsonUtil.reformat(json4);
		String json6 = RjsonUtil.reformat(json5);
		
		Assert.assertEquals(json1, json2);
		Assert.assertEquals(json2, json3);
		
		Assert.assertEquals(json4, json6);
	}

	@Test
	public void verifyDefaultJsonObject() throws IOException {
		Object expectedObject = RjsonUtil.fileAsObject("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String expectedJson = serializer().toJson(expectedObject);
		String actualJson = serializer().toJson(Rjson.newInstance());
		zen.Assert.thatEquals(expectedJson, actualJson);
	}

	@Test
	public void verifyDefaultJsonObjectIgnoringModifiers() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_ignoring_modifiers.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().andIgnoreModifiers());
		zen.Assert.thatEquals(expectedJson, actualJson);
	}

	@Test
	public void verifyDefaultJsonObjectWithCustomObjectToJsonTransformer() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_object_to_json_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleObjectToJsonTransformer()));
		zen.Assert.thatEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	// @Test
	public void verifyDefaultJsonObjectWithMultipleInstancesOfSameCustomObjectToJsonTransformer() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_object_to_json_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleObjectToJsonTransformer()).and(new SampleObjectToJsonTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithCustomJsonToObjectTransformer() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_json_to_object_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleJsonToObjectTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	// @Test
	public void verifyDefaultJsonObjectWithMultipleInstancesOfSameCustomJsonToObjectTransformer() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/rjson_object_with_custom_json_to_object_transformer.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and(new SampleJsonToObjectTransformer()).and(new SampleJsonToObjectTransformer()));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	@Test
	public void verifyDefaultJsonObjectWithCustomTransformerAsNull() throws IOException {
		String expectedJson = RjsonUtil.fileAsString("./src/test/java/DATA-rjson.Rjson/default_rjson_object.txt");
		String actualJson = serializer().toJson(Rjson.newInstance().and((ObjectToJsonTransformer) null));
		Assert.assertEquals(StringUtils.deleteWhitespace(expectedJson), StringUtils.deleteWhitespace(actualJson));
	}

	private Rjson serializer() {
		return Rjson.newInstance().andIgnoreModifiers();
	}
}