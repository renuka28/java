package org.renuka.learn.java.aop.service;

import org.renuka.learn.java.aop.model.Circle;
import org.renuka.learn.java.aop.model.Triangle;

public class ShapeService {
	
	private Circle circle;
	private Triangle triangle;
	
	public Circle getCircle() {
		System.out.println("ShapeService.getCircle Called");
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	public Triangle getTriangle() {
		return triangle;
	}
	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

}
