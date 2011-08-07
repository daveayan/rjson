package rjson.domain;

public class Scooter implements Drivable {
	public int numberOfWheels() {
		return this.numberOfWheels;
	}
	public String toString() {
		return this.getClass().getName() + " : " + numberOfWheels;
	}
	private int numberOfWheels = 2;
}