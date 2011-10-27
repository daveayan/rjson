package rjson.domain;

public class ObjectWithStatic {
	@SuppressWarnings("unused") private static String staticString = "This is static";
	@SuppressWarnings("unused") private String nonStaticString = "This is not static";
	
	public ObjectWithStatic() {}
	
	public ObjectWithStatic(boolean clear) {
		staticString = "";
		nonStaticString = "";
	}
}