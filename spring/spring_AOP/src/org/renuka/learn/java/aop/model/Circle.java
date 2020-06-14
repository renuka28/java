package org.renuka.learn.java.aop.model;

import org.renuka.learn.java.aop.aspect.Loggable;

public class Circle {
	private String name;
	private int center = 100;
	

	@Loggable
	public int getCenter() {
		return center;
	}

	public void setCenter(int center) {
		this.center = center;
		System.out.println("Circle.setCenter called");
	}
	
	public void raiseException(String str) throws RuntimeException {
		System.out.println("Circle.raiseException called - Throwing Exception");
		throw new RuntimeException();
	}

	@Loggable
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		System.out.println("Circle.setName called");
	}
	
	public String setNameAndReturn(String name) {
		this.name = name;
		System.out.println("Circle.setNameAndReturn called");
		return name + " - being returned";
	}

}
