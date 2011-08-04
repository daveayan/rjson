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
import transformers.Context;

public class ToJsonTransformationUtils {
	public static final String MESSAGE_ERROR = "ERROR - ";
	

	public static Rjson rjson(Context context) {
		return (Rjson) context.get("rjson");
	}
	public static Printer printer(Context context) {
		return (Printer) context.get("printer");
	}
	public static transformers.Transformer transformer(Context context) {
		return (transformers.Transformer) context.get("transformer");
	}

	public static boolean canConvertToJson(Object object) {
		return false;
	}

	public static void delegateHandlingOf(Object from, Class<?> targetClass, Context context) {
		transformer(context).transform(from, targetClass, context);
	}
	
	public static void delegateHandlingOf(Object object, Printer printer, Rjson rjson) {
//		rjson.convertToJson(object, printer);
	}

	public static void printErrorNotImplementedYet(Object object, Printer printer) {
		printer.print(MESSAGE_ERROR + "Serializing " + object.getClass().getName() + " is not implemented yet");
	}

	public static void printClassName(Object object, Printer printer) {
		printer.indent();
		printer.print("\"class\": ");
		printData(object.getClass().getName(), printer);
		hasMoreElements(printer);
	}

	public static void printFieldName(String fieldName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + fieldName + "\": ");
	}

	public static void printMapKeyName(String keyName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + keyName + "\": ");
	}

	public static void printData(String data, Printer printer) {
		printer.print("\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"");
	}

	public static void printData(Object object, Printer printer) {
		printer.print("" + object + "");
	}
	
	public static String formatData(String data) {
		return "\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"";
	}

	public static String formatData(Object object) {
		return "" + object + "";
	}

	public static void hasMoreElements(Printer printer) {
		printer.print(", ");
	}
	
	private ToJsonTransformationUtils() {}
}