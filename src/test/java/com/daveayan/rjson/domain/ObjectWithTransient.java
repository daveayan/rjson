package com.daveayan.rjson.domain;

public class ObjectWithTransient {
	@SuppressWarnings("unused") private transient String transientString = "This is transient";
	@SuppressWarnings("unused") private String nonTransientString = "This is not transient";
}