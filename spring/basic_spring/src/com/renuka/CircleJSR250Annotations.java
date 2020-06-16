package com.renuka;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CircleJSR250Annotations implements Shape{
	
	private SimplePoint center;
	
	public SimplePoint getCenter() {
		return center;
	}


	@Resource(name="pointB")
	public void setCenter(SimplePoint center) {
		this.center = center;
	}

	@Override
	public void draw() {
		System.out.println("Circle circleJSR250Annotations with Shape Interface drawn from ApplicationContext");
		System.out.println("center = (" + center.getX() + "," + center.getY() + ")");	
	} 
	
	@PostConstruct
	public void initializedCircleUsingAnnotation() {
		System.out.println("initializedCircleUsingAnnotation called via annotations");
	}
	
	@PreDestroy
	public void destroyCircleUsingAnnotation() {
		System.out.println("destroyCircleUsingAnnotation called via annotations");
	}

}
 