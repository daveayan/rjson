package rjson.comparator;

import transformers.Transformer;

public class JsonComparator {
	private transformers.Transformer json_comparator;
	
	public static JsonComparator newInstance() {
		JsonComparator json_comparator = new JsonComparator();
		json_comparator.initialize();
		return json_comparator;
	}
	
	private void initialize() {
		this.json_comparator = Transformer.newInstance().clear().and_b(new JsonObjectComparator()).and_b(new JsonArrayComparator());
	}
}