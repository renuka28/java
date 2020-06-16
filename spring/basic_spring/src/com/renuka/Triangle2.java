package com.renuka;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Triangle2 implements InitializingBean, DisposableBean {

	private Point pointA;
	private Point pointB;
	private Point pointC;
	private List<Point> points;
	
	public void draw() {
		System.out.println("Triangle2 drawn from ApplicationContext");		
	}
	public Point getPointA() {
		return pointA;
	}
	public Point getPointB() {
		return pointB;
	}
	public Point getPointC() {
		return pointC;
	}
	public List<Point> getPoints() {
		return points;
	}
	
	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}
	
	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}
	
	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}


	public void setPoints(List<Point> points) {
		this.points = points;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// method will be called after bean has beeninitiatlized 
		System.out.println("Triangle2 bean initilized");
		
	}
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		// method will be called before disposing bean
		System.out.println("Triangle2 bean being disposed");
		
		
	}
	

	//this is defaultInit method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultInit() {
		System.out.println("DEFAULT INIT - Triangle 2 -  Init configured using XML - Triangle2 bean being initialized");
	}
	
	//this is defaultDestroy method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultDestroy() {
		System.out.println("DEFAULT DESTROY - Triangle 2 - Destory configured using XML - Triangle2 bean being destroyed");
	}



}
