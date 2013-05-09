package com.daveayan.rjson.domain;

public class ObjectWithFinal {
	@SuppressWarnings("unused") private final String finalString = "This is final";
	@SuppressWarnings("unused") private String nonFinalString = "This is not final";
	
	public ObjectWithFinal() {}
	
	public ObjectWithFinal(boolean clear) {
		nonFinalString = "";
	}
}