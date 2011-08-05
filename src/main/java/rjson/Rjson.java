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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import rjson.printer.Printer;
import rjson.printer.StringBufferPrinter;
import rjson.transformer.JsonToObjectTransformer;
import rjson.transformer.ObjectToJsonTransformer;
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
import transformers.Context;
import transformers.Transformer;

public class Rjson {
	private transformers.Transformer object_to_json_transformer;
	private transformers.Transformer json_to_object_transformer;
	private boolean ignoreModifiers = false;

	public static Rjson newInstance() {
		Rjson rjson = new Rjson();
		rjson.initialize();
		return rjson;
	}

	public Rjson with(ObjectToJsonTransformer transformer) {
		this.object_to_json_transformer.and_a(transformer);
		return this;
	}
	
	public Rjson and(ObjectToJsonTransformer transformer) {
		return with(transformer);
	}
	
	public Rjson with(JsonToObjectTransformer transformer) {
		this.json_to_object_transformer.and_a(transformer);
		return this;
	}
	
	public Rjson and(JsonToObjectTransformer transformer) {
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

	public Object toObject(String json) {
		return toObject(json, Object.class);
	}
	
	public Object toObject(String json, Class<?> to) {
		JSONTokener tokener = new JSONTokener(json);
		try {
			return convertToObject(tokener, to);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String toJson(Object object) {
		Printer printer = new StringBufferPrinter();
		Context context = Context.newInstance().put("rjson", this).and("printer", printer);
		object_to_json_transformer.transform(object, String.class, context);
		return printer.getOutput();
	}

	private Object convertToObject(JSONTokener tokener, Class<?> to) throws JSONException {
		char firstChar = tokener.nextClean();
		if (firstChar == '\"') {
			return json_to_object_transformer.transform(tokener.nextString('\"'), to, null);
		}
		if (firstChar == '{') {
			tokener.back();
			return json_to_object_transformer.transform(new JSONObject(tokener), to, null);
		}
		if (firstChar == '[') {
			tokener.back();
			return json_to_object_transformer.transform(new JSONArray(tokener), to, null);
		}
		if (Character.isDigit(firstChar)) {
			tokener.back();
			return json_to_object_transformer.transform(tokener.nextValue(), to, null);
		}
		tokener.back();
		return json_to_object_transformer.transform(tokener.nextValue(), to, null);
	}

	private void initialize() {
		setUpDefaultObjectToJsonTransformers();
		setUpDefaultJsonToObjectTransformers();
	}

	private void setUpDefaultObjectToJsonTransformers() {
		this.object_to_json_transformer = Transformer.newInstance().clear()
			.with_default_transformer(new FieldBasedTransformer())
			.and_b(new LeafBooleanTransformer())
			.and_b(new LeafBooleanTransformer())
			.and_b(new LeafCharacterTransformer())
			.and_b(new LeafDateTransformer())
			.and_b(new LeafNumberTransformer())
			.and_b(new LeafPrimitiveTransformer())
			.and_b(new LeafStringTransformer())
			.and_b(new IterableTransformer())
			.and_b(new MapTransformer())
			.and_b(new ArrayTransformer());
	}
	
	private void setUpDefaultJsonToObjectTransformers() {
		this.json_to_object_transformer = Transformer.newInstance().clear()
			.with_b(new JsonBooleanTransformer())
			.and_b(new JsonIntegerTransformer())
			.and_b(new JsonStringTransformer())
			.and_b(new JsonDoubleTransformer())
			.and_b(new JsonArrayTransformer())
			.and_b(new JsonObjectAsMapTransformer())
			.and_b(new JsonObjectTransformer());
	}

	public boolean ignoreModifiers() {
		return ignoreModifiers;
	}

	private Rjson() {
	}
}