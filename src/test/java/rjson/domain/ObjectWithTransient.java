package rjson.domain;

public class ObjectWithTransient {
	private transient String transientString = "This is transient";
	private String nonTransientString = "This is not transient";
}