package rjson;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Test;

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
	
	public void tokenize() throws IOException, JSONException {
		JSONTokener tokener = new JSONTokener(fileAsString("./src/test/java/DATA-rjson.domain.Person/fully-loaded-person-object-respecting-modifiers.txt"));
		System.out.println(tokener);
		System.out.println(tokener.nextValue());
	}
	
	private Rjson deSerializer() {
		return Rjson.newInstance();
	}
	
	private String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}