package com.renuka;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Point implements InitializingBean, DisposableBean{

	private int x;
	private int y;
	public Point() {
		x = 0;
		y = 0;
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// method will be called after bean has beeninitiatlized 
		System.out.println("Initializing Bean INIT - Point bean initilized");
	}
	//this is defaultDestroy method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultDestroy() {
		System.out.println("DEFAULT DESTROY - Point - Destory configured using XML - Point bean being destroyed");
	}
	//this is defaultInit method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultInit() {
		System.out.println("DEFAULT INIT - Point -  Init configured using XML - Point bean being initialized");
	}
	
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		// method will be called before disposing bean
		System.out.println("Disposable Bean DESTROY - Point bean being disposed");		
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	//this is clean method that is being called without using any interfaces. We use spring configuration xml file to configure this
	public void myDestory() {
		System.out.println("BEAN LEVEL - Destory configured using XML - Point bean being destroyed");
	}
	
	//this is init method that is being called without using any interfaces. We use spring configuration xml file to configure this
	public void myInit() {
		System.out.println("BEAN LEVEL - Init configured using XML - Point bean being initialized");
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
 