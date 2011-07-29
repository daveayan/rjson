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
package rjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import rjson.printer.Printer;
import rjson.printer.StringBufferPrinter;
import rjson.transformer.JsonToObjectTransformer;
import rjson.transformer.ObjectToJsonTransformer;
import rjson.transformer.Transformer;
import rjson.transformer.tojson.ArrayTransformer;
import rjson.transformer.tojson.FieldBasedTransformer;
import rjson.transformer.tojson.IterableTransformer;
import rjson.transformer.tojson.LeafBooleanTransformer;
import rjson.transformer.tojson.LeafCharacterTransformer;
import rjson.transformer.tojson.LeafDateTransformer;
import rjson.transformer.tojson.LeafNumberTransformer;
import rjson.transformer.tojson.LeafPrimitiveTransformer;
import rjson.transformer.tojson.LeafStringTransformer;
import rjson.transformer.tojson.MapTransformer;
import rjson.transformer.toobject.JsonArrayTransformer;
import rjson.transformer.toobject.JsonBooleanTransformer;
import rjson.transformer.toobject.JsonDoubleTransformer;
import rjson.transformer.toobject.JsonIntegerTransformer;
import rjson.transformer.toobject.JsonObjectAsMapTransformer;
import rjson.transformer.toobject.JsonObjectTransformer;
import rjson.transformer.toobject.JsonStringTransformer;

public class Rjson {
	private List<ObjectToJsonTransformer> default_object_to_json_transformers = null;
	private Map<String, ObjectToJsonTransformer> custom_object_to_json_transformers = null;
	private List<JsonToObjectTransformer> default_json_to_object_transformers = null;
	private Map<String, JsonToObjectTransformer> custom_json_to_object_transformers = null;
	private boolean ignoreModifiers = false;

	public static Rjson newInstance() {
		Rjson rjson = new Rjson();
		rjson.initialize();
		return rjson;
	}

	public Rjson with(Transformer transformer) {
		if (transformer instanceof ObjectToJsonTransformer) {
			this.registerObjectToJsonTransformer((ObjectToJsonTransformer) transformer, true);
		}
		if (transformer instanceof JsonToObjectTransformer) {
			this.registerJsonToObjectTransformer((JsonToObjectTransformer) transformer, true);
		}
		return this;
	}

	public Rjson and(Transformer transformer) {
		return with(transformer);
	}

	public Rjson andIgnoreModifiers() {
		this.ignoreModifiers = true;
		return this;
	}

	public Rjson andDoNotIgnoreModifiers() {
		this.ignoreModifiers = false;
		return this;
	}

	public Object fromJson(String json) {
		JSONTokener tokener = new JSONTokener(json);
		try {
			return convertToObject(tokener);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object convertToObject(JSONTokener tokener) throws JSONException {
		char firstChar = tokener.nextClean();
		if (firstChar == '\"') {
			return jsonObjectToObjectControl(tokener.nextString('\"'));
		}
		if (firstChar == '{') {
			tokener.back();
			return jsonObjectToObjectControl(new JSONObject(tokener));
		}
		if (firstChar == '[') {
			tokener.back();
			return jsonObjectToObjectControl(new JSONArray(tokener));
		}
		if (Character.isDigit(firstChar)) {
			tokener.back();
			return jsonObjectToObjectControl(tokener.nextValue());
		}
		tokener.back();
		return jsonObjectToObjectControl(tokener.nextValue());
	}

	public Object jsonObjectToObjectControl(Object jf) {
		for (JsonToObjectTransformer transformer : custom_json_to_object_transformers.values()) {
			if (transformer.canConvertToObject(jf)) {
				return transformer.transformJsonToObject(jf, this);
			}
		}
		for (JsonToObjectTransformer transformer : default_json_to_object_transformers) {
			if (transformer.canConvertToObject(jf)) {
				return transformer.transformJsonToObject(jf, this);
			}
		}
		return null;
	}

	public String toJson(Object object) {
		Printer printer = new StringBufferPrinter();
		convertToJson(object, printer);
		return printer.getOutput();
	}

	public void convertToJson(Object object, Printer printer) {
		try {
			for (ObjectToJsonTransformer transformer : custom_object_to_json_transformers.values()) {
				if (transformer.canConvertToJson(object)) {
					transformer.transformToJson(object, printer, this);
					return;
				}
			}
			for (ObjectToJsonTransformer transformer : default_object_to_json_transformers) {
				if (transformer.canConvertToJson(object)) {
					transformer.transformToJson(object, printer, this);
					return;
				}
			}
		} catch (Throwable th) {
			th.printStackTrace();
			// System.out.println("ERROR CONVERTING : " +
			// object.getClass().getName());
			// printer.print("ERROR CONVERTING : " +
			// object.getClass().getName());
			// printer.print(th.getMessage());
		}
	}

	private void initialize() {
		setUpdefaultObjectToJsonTransformers();
		setUpdefaultJsonToObjectTransformers();
		custom_object_to_json_transformers = new HashMap<String, ObjectToJsonTransformer>();
		custom_json_to_object_transformers = new HashMap<String, JsonToObjectTransformer>();
	}

	private void setUpdefaultObjectToJsonTransformers() {
		if (default_object_to_json_transformers != null)
			return;
		default_object_to_json_transformers = new ArrayList<ObjectToJsonTransformer>();

		default_object_to_json_transformers.add(new LeafBooleanTransformer());
		default_object_to_json_transformers.add(new LeafCharacterTransformer());
		default_object_to_json_transformers.add(new LeafDateTransformer());
		default_object_to_json_transformers.add(new LeafNumberTransformer());
		default_object_to_json_transformers.add(new LeafPrimitiveTransformer());
		default_object_to_json_transformers.add(new LeafStringTransformer());
		default_object_to_json_transformers.add(new IterableTransformer());
		default_object_to_json_transformers.add(new MapTransformer());
		default_object_to_json_transformers.add(new ArrayTransformer());
		default_object_to_json_transformers.add(new FieldBasedTransformer());
	}

	private void setUpdefaultJsonToObjectTransformers() {
		if (default_json_to_object_transformers != null)
			return;
		default_json_to_object_transformers = new ArrayList<JsonToObjectTransformer>();

		default_json_to_object_transformers.add(new JsonBooleanTransformer());
		default_json_to_object_transformers.add(new JsonIntegerTransformer());
		default_json_to_object_transformers.add(new JsonStringTransformer());
		default_json_to_object_transformers.add(new JsonDoubleTransformer());
		default_json_to_object_transformers.add(new JsonArrayTransformer());
		default_json_to_object_transformers.add(new JsonObjectAsMapTransformer());
		default_json_to_object_transformers.add(new JsonObjectTransformer());
	}

	private void registerObjectToJsonTransformer(ObjectToJsonTransformer transformer, boolean replaceIfExists) {
		if (transformer == null)
			return;
		String key = transformer.getClass().getName();
		if (custom_object_to_json_transformers.containsKey(key)) {
			if (replaceIfExists) {
				custom_object_to_json_transformers.remove(key);
				custom_object_to_json_transformers.put(key, transformer);
			}
		} else {
			custom_object_to_json_transformers.put(key, transformer);
		}
	}

	private void registerJsonToObjectTransformer(JsonToObjectTransformer transformer, boolean replaceIfExists) {
		if (transformer == null)
			return;
		String key = transformer.getClass().getName();
		if (custom_json_to_object_transformers.containsKey(key)) {
			if (replaceIfExists) {
				custom_json_to_object_transformers.remove(key);
				custom_json_to_object_transformers.put(key, transformer);
			}
		} else {
			custom_json_to_object_transformers.put(key, transformer);
		}
	}

	public boolean ignoreModifiers() {
		return ignoreModifiers;
	}

	private Rjson() {
	}
}