package com.renuka;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Triangle implements ApplicationContextAware, BeanNameAware, InitializingBean, DisposableBean {


	private String type;
	private int height;
	private int angle;
	private Point pointA; 
	private Point pointB;
	private Point pointC;
	private Point pointZero;
	private ApplicationContext context = null;
	private String beanName = null;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
	}

	@Override
	public void setBeanName(String beanName) {
		// TODO Auto-generated method stub
		this.beanName = beanName;
		System.out.println("bean name = " + beanName);
	}
	
	
	
	private List<Point> points;
	
	public List<Point> getPoints() {
		return points;
	}


	public void setPoints(List<Point> points) {
		this.points = points;
	}


	public Point getPointZero() {
		return pointZero;
	}


	public void setPointZero(Point pointZero) {
		this.pointZero = pointZero; 
	}


	public Point getPointA() {
		return pointA;
	}


	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}


	public Point getPointB() {
		return pointB;
	}


	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}


	public Point getPointC() {
		return pointC;
	}


	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}

	
	
	public int getAngle() {
		return angle;
	}


	public void setAngle(int angle) {
		this.angle = angle;
	}


	public int getHeight() {
		return height;
	}
	

	public Triangle(String type) {
		this.type = type + " set by 'Triangle(String type)' constructor";
	}
	
	public Triangle(int height) {
		this.height = height;
	}
	
	public Triangle(String type, int height) {
		this.type = type + " set by 'Triangle(String type, int height)' constructor";
		this.height = height;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type + "set by setter";
	}

	public void draw() {
		System.out.println("Triangle drawn from ApplicationContext");
		System.out.print("pointA = (" + pointA.getX() + "," + pointA.getY() + "), ");	
		System.out.print("pointB = (" + pointB.getX() + "," + pointB.getY() + "), ");
		System.out.print("pointC = (" + pointC.getX() + "," + pointC.getY() + ")");	
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub 
		// method will be called after bean has beeninitiatlized 
		System.out.println("Triangle bean initilized");
		
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		// method will be called before disposing bean
		System.out.println("Triangle bean being disposed");
		
	}
	
	//this is init method that is being called without using any interfaces. We use spring configuration xml file to configure this
	public void myInit() {
		System.out.println("BEAN LEVEL - Init configured using XML - Triangle bean being initialized");
	}
	
	//this is clean method that is being called without using any interfaces. We use spring configuration xml file to configure this
	public void myDestory() {
		System.out.println("BEAN LEVEL - Destory configured using XML - Triangle bean being destroyed");
	}
	
	
	//this is defaultInit method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultInit() {
		System.out.println("DEFAULT INIT - Triangle -  Init configured using XML - Triangle bean being initialized");
	}
	
	//this is defaultDestroy method configured at beans level and is being called without using any interfaces. We use spring configuration xml file to configure this
	public void defaultDestroy() {
		System.out.println("DEFAULT DESTROY - Triangle - Destory configured using XML - Triangle bean being destroyed");
	}

}
