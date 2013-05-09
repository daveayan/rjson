package rjson.domain;

public class ObjectWithUrlString {
	private String url;
	public ObjectWithUrlString() {
		this.url = "http://daveayan.com";
	}
	
	public String toString() {
		return "ObjectWithUrlString : " + url;
	}
}