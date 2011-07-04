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

import java.util.Iterator;
import java.util.Map;

import rjson.Rjson;
import rjson.printer.Printer;

public class MapTransformer extends AbstractTransformer {
	public void transform(Object object, Printer printer, Rjson rjson) {
		printer.print("{");
		printer.increaseIndent();
		printer.indent();
		if (object == null)
			return;

		Map<?, ?> objectMap = (Map<?, ?>) object;
		Iterator<?> iter = objectMap.keySet().iterator();
		while (true) {
			if (!iter.hasNext())
				break;
			Object key = iter.next();
			Object newObject = objectMap.get(key);
			printMapKeyName(key.toString(), printer);
			delegateHandlingOf(newObject, printer, rjson);
			if (iter.hasNext())
				hasMoreElements(printer);
		}
		printer.printNewLine();
		printer.decreaseIndent();
		printer.indent();
		printer.print("}");
	}

	public boolean canHandle(Object object) {
		if (object instanceof java.util.Map<?, ?>) {
			return true;
		}
		return false;
	}
}