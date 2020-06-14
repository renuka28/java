package org.renuka.learn.java.aop;

import org.renuka.learn.java.aop.model.Circle;
import org.renuka.learn.java.aop.model.Triangle;
import org.renuka.learn.java.aop.service.FactoryService;
import org.renuka.learn.java.aop.service.ShapeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_aop.xml");
		aopSetup(ctx);
		aopWildCardDemo(ctx);
		aopPointCutDemo(ctx);
		aopJoinPointsDemo(ctx);
		aopAfterDemo(ctx);	
		aopAroundDemo(ctx);
		aopCustomAnnotationDemo(ctx);
		aopXMLAnnotationDemo(ctx);		
		demoAopProxies();
		
	}
	
	public static void demoAopProxies() {
		System.out.println("--------------------------------------------------------");
		FactoryService factoryService = new FactoryService();
		//this call actualy provides us with a ShapeServiceProxy object which is a childclass of ShapeService. 
		ShapeService shapeService = (ShapeService)factoryService.getBean("shapeService");
		//this will trigger our customer logging aspect method
		Circle circle = (Circle) shapeService.getCircle();
		
	}
	
	public static void aopXMLAnnotationDemo(ApplicationContext ctx) {
		
		ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
		System.out.println("--------------------------------------------------------");
		Circle circle = shapeSerivce.getCircle();
		System.out.println("--------------------------------------------------------");
		circle.setName("XML Aspect  - name set");
		System.out.println("--------------------------------------------------------");
		System.out.println(circle.getName());
//		//this will trigger custom loggable annotation
//		System.out.println(circle.getCenter());		
//		System.out.println(circle.getName());
	
	}
	

	public static void aopCustomAnnotationDemo(ApplicationContext ctx) {
			
			ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
			System.out.println("--------------------------------------------------------");
			Circle circle = shapeSerivce.getCircle();
			//this will trigger custom loggable annotation
			System.out.println(circle.getCenter());		
			System.out.println(circle.getName());
		
	}
	
	public static void aopAroundDemo(ApplicationContext ctx) {
			
			ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
			System.out.println("--------------------------------------------------------");
			Circle circle = shapeSerivce.getCircle();	
			System.out.println(circle.getName());		
			circle.setName("gubol - set now");
			System.out.println(circle.getName());
	}
	
	public static void aopAfterDemo(ApplicationContext ctx) {
		
		ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
		System.out.println("--------------------------------------------------------");
		Circle circle = shapeSerivce.getCircle();		
		circle.setName("gubol");
		System.out.println("--------------------------------------------------------");
		try {
			circle.raiseException("throws exception");
		}
		catch(Exception ex) {}
		System.out.println("--------------------------------------------------------");
		circle.setNameAndReturn("Gubol - Set name and Return");
	}
	
	public static void aopJoinPointsDemo(ApplicationContext ctx) {
			
			ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
			Circle circle = shapeSerivce.getCircle();	
			System.out.println(circle.getName());
			System.out.println(circle.getCenter());	
			circle.setName("gubol");
			System.out.println(circle.getName());
	}
	public static void aopPointCutDemo(ApplicationContext ctx) {
		
		ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
		Circle circle = shapeSerivce.getCircle();		
		System.out.println(circle.getName());
		System.out.println(circle.getCenter());		
		System.out.println(shapeSerivce.getTriangle().getName());
	}
	
	public static void aopWildCardDemo(ApplicationContext ctx) {
		
		ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
		Circle circle = shapeSerivce.getCircle();
		System.out.println(circle.getName());
		System.out.println(circle.getCenter());
		System.out.println(shapeSerivce.getTriangle().getName());
		
		
	}
	public static void aopSetup(ApplicationContext ctx) {
		
		ShapeService shapeSerivce = ctx.getBean("shapeService", ShapeService.class);
		System.out.println(shapeSerivce.getCircle().getName());
		System.out.println(shapeSerivce.getTriangle().getName());
	}
}
