package rjson.transformer;

import java.lang.ref.SoftReference;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import transformers.Context;

public abstract class BaseTransformer implements ObjectToJsonTransformer {
	private static Log log = LogFactory.getLog(BaseTransformer.class);

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
}