package com.daveayan.rjson.domain;

public class RecursiveObject {
	Person person = null;
	RecursiveObject recursiveObject = this;
	
	public RecursiveObject() {
		person = Person.getFullyLoadedInstance();
	}
}