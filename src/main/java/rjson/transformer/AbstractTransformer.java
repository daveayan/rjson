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

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.utils.RjsonUtil;

public abstract class AbstractTransformer implements ObjectToJsonTransformer {
	public static final String MESSAGE_ERROR = "ERROR - ";

	public boolean canConvertToJson(Object object) {
		return false;
	}

	public void delegateHandlingOf(Object object, Printer printer, Rjson rjson) {
		rjson.convertToJson(object, printer);
	}

	public void printErrorNotImplementedYet(Object object, Printer printer) {
		printer.print(MESSAGE_ERROR + "Serializing " + object.getClass().getName() + " is not implemented yet");
	}

	protected void printClassName(Object object, Printer printer) {
		printer.indent();
		printer.print("\"class\": ");
		printData(object.getClass().getName(), printer);
		hasMoreElements(printer);
	}

	protected void printFieldName(String fieldName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + fieldName + "\": ");
	}

	protected void printMapKeyName(String keyName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + keyName + "\": ");
	}

	protected void printData(String data, Printer printer) {
		printer.print("\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"");
	}

	protected void printData(Object object, Printer printer) {
		printer.print("" + object + "");
	}

	protected void hasMoreElements(Printer printer) {
		printer.print(", ");
	}
}