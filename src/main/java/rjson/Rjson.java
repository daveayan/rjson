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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package rjson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import rjson.printer.Printer;
import rjson.printer.StringBufferPrinter;
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
import rjson.utils.RjsonUtil;

public class Rjson {
	private static List<ObjectToJsonTransformer> default_object_to_json_transformers = null;
	private static Map<String, ObjectToJsonTransformer> custom_object_to_transformers = null;
	private boolean ignoreModifiers = false;

	public static Rjson newInstance() {
		Rjson rjson = new Rjson();
		rjson.initialize();
		return rjson;
	}

	public Rjson with(List<ObjectToJsonTransformer> transformers) {
		if (transformers == null)
			return this;
		for (ObjectToJsonTransformer t : transformers) {
			this.registerTransformer(t, true);
		}
		return this;
	}

	public Rjson with(ObjectToJsonTransformer transformer) {
		this.registerTransformer(transformer, true);
		return this;
	}

	public Rjson and(ObjectToJsonTransformer transformer) {
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

	private void setField(Field field, Object objectToBeReturned, Object value) {
		try {
			if (field.getType().isPrimitive() && value == null)
				return;
			if (field.getType().getName().trim().equals("java.util.Date"))
				return;
			if (value.getClass().getName().equals("java.lang.Double")) {
				if (field.getClass().getName().equals("java.lang.Float") || field.getType().getName().equals("float")) {
					Double dblValue = (Double) value;
					field.set(objectToBeReturned, dblValue.floatValue());
					return;
				}
			}
			field.set(objectToBeReturned, value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return null;
	}

	private Object jsonObjectToObject(JSONObject jo) {
		try {
			Object objectToBeReturned = RjsonUtil.objectFor(jo.getString("class"));
			List<Field> fields = RjsonUtil.getAllFieldsIn(objectToBeReturned);
			Iterator<Field> iter = fields.iterator();
			while (iter.hasNext()) {
				Field field = iter.next();
				RjsonUtil.makeAccessible(field);
				if (jo.has(field.getName())) {
					Object jsonField = jo.get(field.getName());
					String jsonFieldContents = jsonField.toString();
					System.out.println("***===>>>" + field.getType().getName() + " : " + " : " + jsonField.getClass().getName() + " : " + jsonFieldContents
							+ " <<<===***");
					Object object = jsonObjectToObjectControl(jsonField);
					setField(field, objectToBeReturned, object);
				}
			}
			return objectToBeReturned;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object jsonObjectToObjectControl(Object jf) {
		if (jf instanceof JSONObject) {
			if (((JSONObject) jf).has("class"))
				return jsonObjectToObject((JSONObject) jf);
			else
				return jsonObjectToObjectMap((JSONObject) jf);
		}
		if (jf instanceof JSONArray) {
			return jsonObjectToObject((JSONArray) jf);
		}
		if (jf instanceof Integer) {
			return jsonObjectToObject((Integer) jf);
		}
		if (jf instanceof Double) {
			return jsonObjectToObject((Double) jf);
		}
		if (jf instanceof String) {
			return jsonObjectToObject((String) jf);
		}
		return null;
	}

	private Object jsonObjectToObjectMap(JSONObject jo) {
		System.out.println("jsonObjectToObjectMap JSONObject");
		Map<Object, Object> newMap = new HashMap<Object, Object>();
		Iterator<?> iter = jo.getMap().keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			newMap.put(key, jsonObjectToObjectControl(jo.getMap().get(key)));
		}
		return newMap;
	}

	private Object jsonObjectToObject(JSONArray ja) {
		System.out.println("jsonObjectToObject JSONArray");
		List<Object> newList = new ArrayList<Object>();
		for (Object item : ja.getList()) {
			newList.add(jsonObjectToObjectControl(item));
		}
		return newList;
	}

	private Object jsonObjectToObject(Integer integer) {
		System.out.println("jsonObjectToObject Integer");
		return integer;
	}

	private Object jsonObjectToObject(Double dbl) {
		System.out.println("jsonObjectToObject Double");
		return dbl;
	}

	private Object jsonObjectToObject(String string) {
		System.out.println("jsonObjectToObject String");
		if (string != null && string.equals(JSONObject.NULL.toString()))
			return null;
		return string;
	}

	public String toJson(Object object) {
		Printer printer = new StringBufferPrinter();
		convertToJson(object, printer);
		return printer.getOutput();
	}

	public void convertToJson(Object object, Printer printer) {
		try {
			for (ObjectToJsonTransformer transformer : custom_object_to_transformers.values()) {
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
		setUpdefaultTransformers();
		custom_object_to_transformers = new HashMap<String, ObjectToJsonTransformer>();
	}

	private void setUpdefaultTransformers() {
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

	private void registerTransformer(ObjectToJsonTransformer transformer, boolean replaceIfExists) {
		if (transformer == null)
			return;
		String key = transformer.getClass().getName();
		if (custom_object_to_transformers.containsKey(key)) {
			if (replaceIfExists) {
				custom_object_to_transformers.remove(key);
				custom_object_to_transformers.put(key, transformer);
			}
		} else {
			custom_object_to_transformers.put(key, transformer);
		}
	}

	public boolean ignoreModifiers() {
		return ignoreModifiers;
	}

	private Rjson() {
	}
}