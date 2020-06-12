package com.renuka;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Point2 implements InitializingBean, DisposableBean {

	private int x;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	private int y;
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// method will be called after bean has been initiatlized 
		System.out.println("Point2 bean initilized");
		
	}
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		// method will be called before disposing bean
		System.out.println("Point2 bean being disposed");
		
	}
	
	//this is defaultInit method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultInit() {
		System.out.println("DEFAULT INIT - Point2 -  Init configured using XML - Point2 bean being initialized");
	}
	
	//this is defaultDestroy method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultDestroy() {
		System.out.println("DEFAULT DESTROY - Point2 - Destory configured using XML - Point2 bean being destroyed");
	}
}
