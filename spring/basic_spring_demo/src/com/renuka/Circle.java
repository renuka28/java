package com.renuka;

public class Circle implements Shape{
	
	private SimplePoint center;
	
	public SimplePoint getCenter() {
		return center;
	}

	public void setCenter(SimplePoint center) {
		this.center = center;
	}

	@Override	
	public void draw() {
		System.out.println("Circle with Shape Interface drawn from ApplicationContext");
		System.out.print("center = (" + center.getX() + "," + center.getY() + "), ");	
	}

}
