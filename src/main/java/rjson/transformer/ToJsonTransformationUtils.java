package rjson.transformer;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.utils.RjsonUtil;

public class ToJsonTransformationUtils {
	public static final String MESSAGE_ERROR = "ERROR - ";

	public static boolean canConvertToJson(Object object) {
		return false;
	}

	public static void delegateHandlingOf(Object object, Printer printer, Rjson rjson) {
		rjson.convertToJson(object, printer);
	}

	public static void printErrorNotImplementedYet(Object object, Printer printer) {
		printer.print(MESSAGE_ERROR + "Serializing " + object.getClass().getName() + " is not implemented yet");
	}

	protected static void printClassName(Object object, Printer printer) {
		printer.indent();
		printer.print("\"class\": ");
		printData(object.getClass().getName(), printer);
		hasMoreElements(printer);
	}

	protected static void printFieldName(String fieldName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + fieldName + "\": ");
	}

	protected static void printMapKeyName(String keyName, Printer printer) {
		printer.printNewLine();
		printer.indent();
		printer.print("\"" + keyName + "\": ");
	}

	public static void printData(String data, Printer printer) {
		printer.print("\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"");
	}

	protected static void printData(Object object, Printer printer) {
		printer.print("" + object + "");
	}

	protected static void hasMoreElements(Printer printer) {
		printer.print(", ");
	}
	
	private ToJsonTransformationUtils() {}
}