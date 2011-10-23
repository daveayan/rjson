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
 * The copyright notice part of the org.json package and its classes shall be honored.
 * This software shall be used for Good, not Evil.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import rjson.Rjson;

public class RjsonUtil {
	
	public static Object getJsonObject(String json) throws JSONException {
		if(StringUtils.isEmpty(json)) {
			return "";
		}
		JSONTokener tokener = new JSONTokener(json);
		char firstChar = tokener.nextClean();
		if (firstChar == '\"') {
			return tokener.nextString('\"');
		}
		if (firstChar == '{') {
			tokener.back();
			return new JSONObject(tokener);
		}
		if (firstChar == '[') {
			tokener.back();
			return new JSONArray(tokener);
		}
		if (Character.isDigit(firstChar)) {
			tokener.back();
			return tokener.nextValue();
		}
		tokener.back();
		return tokener.nextValue();
	}

	public static String reformat(String inputJson) {
		String lineSeperator = System.getProperty("line.separator");
		inputJson = inputJson.replaceAll(lineSeperator, "");
		inputJson = unEscapeJsonCharactersIn(inputJson);
		try {
			Object object = RjsonUtil.getJsonObject(inputJson);
			if(object instanceof JSONObject) {
				return (new JSONObject(inputJson)).toString(2);
			} else if(object instanceof JSONArray) {
				return (new JSONArray(inputJson)).toString(2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return inputJson;
	}
	
	public static Object fileAsObject(String filename) throws IOException {
		return Rjson.newInstance().toObject(fileAsString(filename));
	}

	public static String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file)).replaceAll("\\r\\n", "\n");
	}

	public static Rjson completeSerializer() {
		return Rjson.newInstance().with(new NullifyDateTransformer()).andRecordAllModifiers();
	}

	public static String escapeJsonCharactersIn(String string) {
		String newString = StringUtils.replace(string, "\"", "");
		newString = StringUtils.replace(newString, "[", "\\[");
		newString = StringUtils.replace(newString, "]", "\\]");
		newString = StringUtils.replace(newString, "{", "\\{");
		newString = StringUtils.replace(newString, "}", "\\}");
		return newString;
	}
	
	public static String unEscapeJsonCharactersIn(String string) {
		String newString = StringUtils.replace(string, "\"", "");
		newString = StringUtils.replace(newString, "\\[", "[");
		newString = StringUtils.replace(newString, "\\]", "]");
		newString = StringUtils.replace(newString, "\\{", "{");
		newString = StringUtils.replace(newString, "\\}", "}");
		return newString;
	}
}