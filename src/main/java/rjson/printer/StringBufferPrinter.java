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
package rjson.printer;

public class StringBufferPrinter implements Printer {
	private static char SINGLE_SPACE = ' ';
	private static String NEWLINE = "\n";
	private int indentspaces = 0;
	StringBuffer buffer = new StringBuffer();

	public void print(String string) {
		if(string.trim().length() > 0) {
			buffer.append(string.trim());
		}
	}

	public void decreaseIndent() {
		indentspaces -= 4;
	}

	public void increaseIndent() {
		indentspaces += 4;
	}

	public void printNewLine() {
		buffer.append(NEWLINE);
	}

	public void indent() {
		for (int i = 0; i < indentspaces; i++) {
			buffer.append(SINGLE_SPACE);
		}
	}

	public String getOutput() {
		return buffer.toString();
	}
	
	public String toString() {
		return getOutput();
	}

	public void reset() {
		buffer = new StringBuffer();
	}
}