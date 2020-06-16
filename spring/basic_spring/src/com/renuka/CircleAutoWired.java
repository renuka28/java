package com.renuka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CircleAutoWired implements Shape{
	
	private SimplePoint center;
	
	public SimplePoint getCenter() {
		return center;
	}


	@Autowired
	@Qualifier("myCirclePoint")
	public void setCenter(SimplePoint center) {
		this.center = center;
	}

	@Override
	public void draw() {
		System.out.println("Circle AutoWired with Shape Interface drawn from ApplicationContext");
		System.out.print("center = (" + center.getX() + "," + center.getY() + ")");	
	}

}
 