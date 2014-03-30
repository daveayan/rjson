/*
 * Copyright (c) 2011 Ayan Dave http://daveayan.com
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
package com.daveayan.rjson;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.daveayan.rjson.domain.Exclusion;
import com.daveayan.rjson.printer.StringBufferPrinter;
import com.daveayan.rjson.transformer.JsonToObjectTransformer;
import com.daveayan.rjson.transformer.ObjectToJsonTransformer;
import com.daveayan.rjson.transformer.tojson.ArrayTransformer;
import com.daveayan.rjson.transformer.tojson.ByteArrayTransformer;
import com.daveayan.rjson.transformer.tojson.FieldBasedTransformer;
import com.daveayan.rjson.transformer.tojson.IgnoreClassTransformation;
import com.daveayan.rjson.transformer.tojson.IterableTransformer;
import com.daveayan.rjson.transformer.tojson.LeafBooleanTransformer;
import com.daveayan.rjson.transformer.tojson.LeafCharacterTransformer;
import com.daveayan.rjson.transformer.tojson.LeafDateTransformer;
import com.daveayan.rjson.transformer.tojson.LeafJodaLocalDateTransformer;
import com.daveayan.rjson.transformer.tojson.LeafNumberTransformer;
import com.daveayan.rjson.transformer.tojson.LeafPrimitiveTransformer;
import com.daveayan.rjson.transformer.tojson.LeafStringTransformer;
import com.daveayan.rjson.transformer.tojson.LeafUUIDTransformer;
import com.daveayan.rjson.transformer.tojson.MapTransformer;
import com.daveayan.rjson.transformer.tojson.NullToJsonTransformer;
import com.daveayan.rjson.transformer.toobject.DateMillisToDateTransformer;
import com.daveayan.rjson.transformer.toobject.JsonArrayToSetTransformer;
import com.daveayan.rjson.transformer.toobject.JsonArrayToVectorTransformer;
import com.daveayan.rjson.transformer.toobject.JsonArrayTransformer;
import com.daveayan.rjson.transformer.toobject.JsonBooleanTransformer;
import com.daveayan.rjson.transformer.toobject.JsonDoubleToFloatTransformer;
import com.daveayan.rjson.transformer.toobject.JsonDoubleTransformer;
import com.daveayan.rjson.transformer.toobject.JsonIntegerTransformer;
import com.daveayan.rjson.transformer.toobject.JsonObjectAsMapTransformer;
import com.daveayan.rjson.transformer.toobject.JsonObjectTransformer;
import com.daveayan.rjson.transformer.toobject.JsonStringTransformer;
import com.daveayan.rjson.transformer.toobject.JsonUUIDTransformer;
import com.daveayan.rjson.transformer.toobject.NullTransformation;
import com.daveayan.rjson.utils.RjsonUtil;
import com.daveayan.transformers.Context;
import com.daveayan.transformers.Transformer;

public class Rjson {
	private static Log log = LogFactory.getLog(Rjson.class);
	private com.daveayan.transformers.Transformer object_to_json_transformer;
	private com.daveayan.transformers.Transformer json_to_object_transformer;
	private List<Exclusion> exclusions = new ArrayList<Exclusion>();
	private boolean recordAllModifiers = false, recordFinal = false, recordStatic = false, format = true;

	public static Rjson newInstance() {
		Rjson rjson = new Rjson();
		rjson.initialize();
		return rjson;
	}
	
	public Object toObject(String json) {
		return toObject(json, Object.class);
	}
	
	public Object toObject(String json, Class<?> to) {
		JSONTokener tokener = new JSONTokener(json);
		try {
			return convertToObject(json, tokener, to);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String toJson(Object object) {
		log.info("Converting to json " + object);
		StringBufferPrinter json_buffer = new StringBufferPrinter();
		Context context = Context.newInstance().put("rjson", this).and("json_buffer", json_buffer).and("cycle_set", new SoftReference<Set<?>>(new HashSet<Object>()));
		object_to_json_transformer.transform(object, String.class, context);
		log.info("json before formatting is : " + json_buffer.toString());
//		return json_buffer.toString();
		String json = json_buffer.toString();
		if(formatJson()) {
			json = RjsonUtil.reformat(json_buffer.toString());
		}
		log.info("json is : " + json);
		return json;
	}

	private Object convertToObject(String json, JSONTokener tokener, Class<?> to) throws JSONException {
		char firstChar = tokener.nextClean();
		if (firstChar == '\"') {
			return json_to_object_transformer.transform(tokener.nextString('\"'), String.class, null);
		}
		if (firstChar == '{') {
			tokener.back();
			JSONObject jsonObject = new JSONObject(tokener);
			if(! jsonObject.has("class_name")) {
				return json_to_object_transformer.transform(jsonObject, HashMap.class, null);
			} else {
				tokener = new JSONTokener(json);
				tokener.nextClean();
			}
		}
		if (firstChar == '[') {
			tokener.back();
			return json_to_object_transformer.transform(new JSONArray(tokener), ArrayList.class, null);
		}
		if (Character.isDigit(firstChar)) {
			tokener.back();
			return json_to_object_transformer.transform(tokener.nextValue(), Double.class, null);
		}
		tokener.back();
		return json_to_object_transformer.transform(tokener.nextValue(), Object.class, null);
	}

	private void initialize() {
		setUpDefaultObjectToJsonTransformers();
		setUpDefaultJsonToObjectTransformers();
	}

	private void setUpDefaultObjectToJsonTransformers() {
		this.object_to_json_transformer = Transformer.newInstance().clear()
			.with_default_transformer(new FieldBasedTransformer())
			.and_b(new NullToJsonTransformer())
			.and_b(new IgnoreClassTransformation())
			.and_b(new LeafBooleanTransformer())
			.and_b(new LeafCharacterTransformer())
			.and_b(new LeafDateTransformer())
			.and_b(new LeafJodaLocalDateTransformer())
			.and_b(new LeafNumberTransformer())
			.and_b(new LeafUUIDTransformer())
			.and_b(new LeafPrimitiveTransformer())
			.and_b(new LeafStringTransformer())
			.and_b(new IterableTransformer())
			.and_b(new MapTransformer())
			.and_b(new ArrayTransformer())
			.and_b(new ByteArrayTransformer());
	}
	
	private void setUpDefaultJsonToObjectTransformers() {
		this.json_to_object_transformer = Transformer.newInstance().clear().setup_built_in_transformers()
			.with_b(new NullTransformation())
			.and_b(new DateMillisToDateTransformer())
			.and_b(new JsonBooleanTransformer())
			.and_b(new JsonIntegerTransformer())
			.and_b(new JsonStringTransformer())
			.and_b(new JsonDoubleTransformer())
			.and_b(new JsonDoubleToFloatTransformer())
			.and_b(new JsonObjectAsMapTransformer())
			.and_b(new JsonObjectTransformer())
			.and_b(new JsonArrayToSetTransformer())
			.and_b(new JsonArrayToVectorTransformer())
			.and_b(new JsonArrayTransformer())
			.and_b(new JsonUUIDTransformer());
	}
	
	public boolean exclude(Field field, Object from, Class<?> to, Context context) {
		for(Exclusion exclusion: exclusions) {
			if(exclusion.exclude(field, from, to, context)) {
				return true;
			}
		}
		return false;
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

	public Rjson andRecordAllModifiers() {
		this.recordAllModifiers = true;
		return this;
	}

	public Rjson andDoNotRecordAllModifiers() {
		this.recordAllModifiers = false;
		return this;
	}
	
	public Rjson andRecordFinal() {
		this.recordFinal = true;
		return this;
	}
	
	public Rjson andDoNotRecordFinal() {
		this.recordFinal = false;
		return this;
	}
	
	public Rjson andRecordStatic() {
		this.recordStatic = true;
		return this;
	}
	
	public Rjson andDoNotRecordStatic() {
		this.recordStatic = false;
		return this;
	}
	
	public Rjson andDoNotFormatJson() {
		this.format = false;
		return this;
	}

	public boolean recordAllModifiers() {
		return recordAllModifiers;
	}
	
	public boolean recordFinal() {
		return recordFinal;
	}
	
	public boolean doNotRecordFinal() {
		return ! recordFinal();
	}
	
	public boolean recordStatic() {
		return recordStatic;
	}
	
	public boolean doNotRecordStatic() {
		return ! recordStatic();
	}
	
	public boolean formatJson() {
		return format;
	}
	
	public Rjson with(Exclusion exclusion) {
		this.exclusions.add(exclusion);
		return this;
	}
	
	public Rjson andWith(Exclusion exclusion) {
		return with(exclusion);
	}

	private Rjson() {
	}
}