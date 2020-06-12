package com.renuka;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Triangle4 implements Shape {
	
	private Point3 pointA; 
	private Point3 pointB;
	private Point3 pointC;

	public void draw() {
		System.out.println("Triangle4 with Shape Interface drawn from ApplicationContext");		
		System.out.print("pointA = (" + pointA.getX() + "," + pointA.getY() + "), ");	
		System.out.print("pointB = (" + pointB.getX() + "," + pointB.getY() + "), ");
		System.out.print("pointC = (" + pointC.getX() + "," + pointC.getY() + ")");
	}

	public Point3 getPointA() {
		return pointA;
	}

	public Point3 getPointB() {
		return pointB;
	}

	public Point3 getPointC() {
		return pointC;
	}

	public void setPointA(Point3 pointA) {
		this.pointA = pointA;
	}

	public void setPointB(Point3 pointB) {
		this.pointB = pointB;
	}
	
	public void setPointC(Point3 pointC) {
		this.pointC = pointC;
	}

}
