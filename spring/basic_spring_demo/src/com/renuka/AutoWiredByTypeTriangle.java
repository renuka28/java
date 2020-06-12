package com.renuka;

public class AutoWiredByTypeTriangle {
	private Point2 autoPointA;
	private Point2 autoPointB;
	private Point2 autoPointC;
	
	
	public Point2 getAutoPointA() {
		return autoPointA;
	}


	public void setAutoPointA(Point2 autoPointA) {
		this.autoPointA = autoPointA;
	}


	public Point2 getAutoPointB() {
		return autoPointB;
	}


	public void setAutoPointB(Point2 autoPointB) {
		this.autoPointB = autoPointB;
	}


	public Point2 getAutoPointC() {
		return autoPointC;
	}


	public void setAutoPointC(Point2 autoPointC) {
		this.autoPointC = autoPointC;
	}
	
	public void draw() {
		System.out.println("AutoWiredByTypeTriangle drawn from ApplicationContext now");		
	}

}
