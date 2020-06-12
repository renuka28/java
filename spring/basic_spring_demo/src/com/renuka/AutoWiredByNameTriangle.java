package com.renuka;

public class AutoWiredByNameTriangle {
	private Point autoPointA;
	private Point autoPointB;
	private Point autoPointC;
	
	
	public Point getAutoPointA() {
		return autoPointA;
	}


	public void setAutoPointA(Point autoPointA) {
		this.autoPointA = autoPointA;
	}


	public Point getAutoPointB() {
		return autoPointB;
	}


	public void setAutoPointB(Point autoPointB) {
		this.autoPointB = autoPointB;
	}


	public Point getAutoPointC() {
		return autoPointC;
	}


	public void setAutoPointC(Point autoPointC) {
		this.autoPointC = autoPointC;
	}
	
	public void draw() {
		System.out.println("AutoWiredByNameTriangle drawn from ApplicationContext now");		
	}


}
