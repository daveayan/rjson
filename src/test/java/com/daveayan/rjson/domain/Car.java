package com.daveayan.rjson.domain;

public class Car implements Drivable {
	public int numberOfWheels() {
		return numberOfWheels;
	}
	public String toString() {
		return this.getClass().getName() + " : " + numberOfWheels;
	}
	private int numberOfWheels = 4;
}