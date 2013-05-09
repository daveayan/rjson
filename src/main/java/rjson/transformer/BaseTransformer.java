package rjson.transformer;

import java.lang.ref.SoftReference;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rjson.Rjson;
import rjson.printer.Printer;
import rjson.utils.RjsonUtil;

import com.daveayan.transformers.Context;

public abstract class BaseTransformer implements ObjectToJsonTransformer {
	private static Log log = LogFactory.getLog(BaseTransformer.class);
	public static final String MESSAGE_ERROR = "ERROR - ";

	public boolean cycleDetectedWith(Object object, Context context) {
		return false;
//		if (detectCycle(object, context)) {
//			log.info("cycle is detected in object : " + object);
//			ToJsonTransformationUtils.printData("ohoh", (StringBuffer) context.get("json_buffer"));
//			return true;
//		}
//		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean detectCycle(Object from, Context context) {
		SoftReference<Set< ? >> soft_reference = (SoftReference<Set< ? >>) context.get("cycle_set");
		Set<Object> cycle_set = (Set<Object>) soft_reference.get();
		return ! cycle_set.add(from);
	}
	
	public void printErrorNotImplementedYet(Object object, Context context) {
		printerIn(context).print(MESSAGE_ERROR + "Serializing " + object.getClass().getName() + " is not implemented yet");
	}

	public void printClassName(Object object, Context context) {
		printerIn(context).indent();
		printerIn(context).print("\"class\": ");
		printData(object.getClass().getName(), context);
		hasMoreElements(context);
	}

	public void printFieldName(String fieldName, Context context) {
		printerIn(context).printNewLine();
		printerIn(context).indent();
		printerIn(context).print("\"" + fieldName + "\": ");
	}

	public void printMapKeyName(String keyName, Context context) {
		printerIn(context).printNewLine();
		printerIn(context).indent();
		printerIn(context).print("\"" + keyName + "\": ");
	}

	public void printData(String data, Context context) {
		printerIn(context).print("\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"");
	}

	public void printData(Object object, Context context) {
		printerIn(context).print("" + object + "");
	}

	public String formatData(String data) {
		return "\"" + RjsonUtil.escapeJsonCharactersIn(data) + "\"";
	}

	public String formatData(Object object) {
		return "" + object + "";
	}

	public void hasMoreElements(Context context) {
		printerIn(context).print(", ");
//		printerIn(context).printNewLine();
//		printerIn(context).indent();
	}
	
	public Printer printerIn(Context context) {
		return (Printer) context.get("json_buffer");
	}
	
	public Rjson rjson(Context context) {
		return (Rjson) context.get("rjson");
	}
}