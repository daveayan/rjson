package rjson.domain;

public class ObjectWithFinalAndStatic {
	
	public ObjectWithFinalAndStatic() {}
	
	public ObjectWithFinalAndStatic(boolean clear) {
		nonFinalString = "";
		staticString = "";
		nonStaticString = "";
	}
	
	@SuppressWarnings("unused") private final String finalString = "This is final";
	@SuppressWarnings("unused") private String nonFinalString = "This is not final";
	@SuppressWarnings("unused") private static String staticString = "This is static";
	@SuppressWarnings("unused") private String nonStaticString = "This is not static";
	@SuppressWarnings("unused") private final static String finalAndStaticString = "This is final and static";
}