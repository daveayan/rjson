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
package rjson.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

import rjson.Rjson;
import rjson.printer.Printer;

public class IterableTransformer extends AbstractTransformer implements JsonToObjectTransformer {

	public Object transformJsonToObject(Object object, Rjson rjson) {
		JSONArray ja = (JSONArray) object;
		System.out.println("jsonObjectToObject JSONArray");
		List<Object> newList = new ArrayList<Object>();
		for (Object item : ja.getList()) {
			newList.add(rjson.jsonObjectToObjectControl(item));
		}
		return newList;
	}

	public void transformToJson(Object object, Printer printer, Rjson rjson) {
		printer.print("[");
		printer.increaseIndent();
		printer.indent();
		if (object == null)
			return;
		Iterator<?> iter = ((Iterable<?>) object).iterator();
		while (true) {
			if (!iter.hasNext())
				break;
			Object newObject = iter.next();
			printer.printNewLine();
			printer.indent();
			delegateHandlingOf(newObject, printer, rjson);
			if (iter.hasNext())
				hasMoreElements(printer);
		}
		printer.printNewLine();
		printer.decreaseIndent();
		printer.indent();
		printer.print("]");
	}

	public boolean canHandle(Object object) {
		if (object != null) {
			if (object instanceof java.lang.Iterable<?>) {
				return true;
			}
		}
		return false;
	}
}