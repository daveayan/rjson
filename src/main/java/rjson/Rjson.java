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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjson.printer.Printer;
import rjson.printer.StringBufferPrinter;
import rjson.transformer.AbstractTransformer;
import rjson.transformer.ArrayTransformer;
import rjson.transformer.FieldBasedTransformer;
import rjson.transformer.IterableTransformer;
import rjson.transformer.MapTransformer;
import rjson.transformer.ToJsonTransformer;
import rjson.transformer.Transformer;

public class Rjson {
	private static AbstractTransformer anyObjectTransformer = null;
	private static List<Transformer> default_transformers = null;
	private static Map<String, Transformer> custom_transformers = null;
	private boolean ignoreModifiers = false;

	public static Rjson newInstance() {
		Rjson rjson = new Rjson();
		rjson.initialize();
		return rjson;
	}

	public Rjson with(List<Transformer> transformers) {
		if (transformers == null)
			return this;
		for (Transformer t : transformers) {
			this.registerTransformer(t, true);
		}
		return this;
	}

	public Rjson with(Transformer transformer) {
		this.registerTransformer(transformer, true);
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

	public String toJson(Object object) {
		Printer printer = new StringBufferPrinter();
		convertToJson(object, printer);
		return printer.getOutput();
	}

	public void convertToJson(Object object, Printer printer) {
		try {
			for (Transformer transformer : custom_transformers.values()) {
				if (transformer.canHandle(object)) {
					transformer.transform(object, printer, this);
					return;
				}
			}
			for (Transformer transformer : default_transformers) {
				if (transformer.canHandle(object)) {
					transformer.transform(object, printer, this);
					return;
				}
			}
			anyObjectTransformer.transform(object, printer, this);
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
		custom_transformers = new HashMap<String, Transformer>();
		anyObjectTransformer = new FieldBasedTransformer();
	}

	private void setUpdefaultTransformers() {
		if (default_transformers != null)
			return;
		default_transformers = new ArrayList<Transformer>();
		AbstractTransformer toJsonTransformer = new ToJsonTransformer();
		AbstractTransformer iterableTransformer = new IterableTransformer();
		AbstractTransformer mapTransformer = new MapTransformer();
		AbstractTransformer arrayTransformer = new ArrayTransformer();

		default_transformers.add(toJsonTransformer);
		default_transformers.add(iterableTransformer);
		default_transformers.add(mapTransformer);
		default_transformers.add(arrayTransformer);
	}

	private void registerTransformer(Transformer transformer, boolean replaceIfExists) {
		if(transformer == null) return;
		String key = transformer.getClass().getName();
		if (custom_transformers.containsKey(key)) {
			if (replaceIfExists) {
				custom_transformers.remove(key);
				custom_transformers.put(key, transformer);
			}
		} else {
			custom_transformers.put(key, transformer);
		}
	}

	public boolean ignoreModifiers() {
		return ignoreModifiers;
	}

	private Rjson() {
	}
}