package com.renuka;   

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.FileSystemResource;

public class DrawingApplication {

	public static void main(String[] args) {
		//demo simple bean creation
		demoSimpleCreation();
		//demo multiple ways of creation and various ways init/destory methods are called
		demoVariousCreations();
		//demo BeanFactoryPostProcessor and PropertyPlaceholderConfigure
		demoBeanFactoryPostProcessors();
		//demo coding to Interface
		demoCodingToInterface();
		//demo spring annotations
		demoAnnotation(); 
		//demo spring annotations
		demoAutoWiredAnnotation();
		//demo JSR-250 Annotations
		demoJSR250Annotations(); 
		//demo component and sterotype annotations
		demoComponentAndStereotypeAnnotation();
		//demo MessageSource properties
		demoMessageSourceProperties();
		//demo eventlistening 
		demoEvents();		
		
	}
	
	public static void demoEvents() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_events.xml");		
		context.registerShutdownHook();
		System.out.println("\nusing 'spring_events.xml'");
		//annotations automatically names the CircleEvents as circleEvents
		//that is changes the first letter to lower case
		Shape shape = (Shape) context.getBean("circleEvents");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}
	
	public static void demoMessageSourceProperties() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_messagesource.xml");		
		System.out.println("\nusing 'spring_messagesource.xml'");
		String message = (String) context.getMessage("greeting", null, "Default hello", null);
		System.out.println(message);
		//lets see if circle gets the message
		//CircleMessageSource is autowired as circleMessageSource
		Shape shape = (Shape) context.getBean("circleMessageSource");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void demoComponentAndStereotypeAnnotation() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_component_and_stereotype_annotations.xml");
		context.registerShutdownHook();
		System.out.println("\nbuilding demoComponentAndStereotypeAnnotation Circle using id "
				+ "'circleComponentAndStereotypeAnnotation' in 'spring_component_and_stereotype_annotations.xml'");
		//annotations automatically names the CircleComponentAndStereotypeAnnotation as circleComponentAndStereotypeAnnotation
		//that is changes the first letter to lower case
		Shape shape = (Shape) context.getBean("circleComponentAndStereotypeAnnotation");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void demoJSR250Annotations() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_JSR250_annotations.xml");
		context.registerShutdownHook();
		System.out.println("\nbuilding spring_JSR250_annotations Circle using id "
				+ "'circleJSR250Annotations' in 'spring_JSR250_annotations.xml'");
		Shape shape = (Shape) context.getBean("circleJSR250Annotations");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void demoAutoWiredAnnotation() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_autowired_annotations.xml");
		context.registerShutdownHook();
		System.out.println("\nbuilding Autowired Circle using id 'circleAutoWired' in 'spring_autowired_annotations.xml");
		Shape shape = (Shape) context.getBean("circleAutoWired");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void demoAnnotation() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_annotations.xml");
		context.registerShutdownHook();
		System.out.println("\nbuilding Circle using interface way. id 'circle' in 'spring_annotations.xml'");
		Shape shape = (Shape) context.getBean("circle");
		shape.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void demoCodingToInterface() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_coding_to_interface.xml");
		context.registerShutdownHook();
		//non coding to interface way
		System.out.println("Demoing coding to interface");
		System.out.println("\nbuilding Triangle using non interface way. id 'triangle4' in 'spring_coding_to_interface.xml' ");
		Triangle4 triangle4 = (Triangle4) context.getBean("triangle4");
		triangle4.draw();
		System.out.println("\nbuilding Circle using non interface way. id 'circle'");
		Circle circle = (Circle) context.getBean("circle");
		circle.draw();	
		
		//now lets code to the Shape Interface
		System.out.println("\nbuilding Triangle using interface way. id 'triangle3'  in 'spring_coding_to_interface.xml'");
		Shape shape = (Shape) context.getBean("triangle4");
		shape.draw();
		System.out.println("\nbuilding Circle using interface way. id 'circle'");
		shape = (Shape) context.getBean("circle");
		shape.draw();	
		//we can change class outside if just change the class it is referring to in the xml
		System.out.println("\nbuilding Circle using interface way. id 'shape'");
		shape = (Shape) context.getBean("shape");
		shape.draw();	
		System.out.println("\n----------------------------------------------------------------------------------------------");		
	}
	

	public static void demoVariousCreations() {
		// level 1
		//straight instantiation
		//Triangle triangle = new Triangle();
		//triangle.draw();
		
		//level 2
		//using bean factory and spring
		//BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
		
		//level 3
		//using spring applicationContext
		//ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");	
		
		//level 4
		//using spring AbstractionApplicationContext to ensure we can shutdown the desktop application. This is not requied for webapps and enterprise apps
		//spring will know when to shutdown. Both ApplicationContext and AbstractApplicationContext have same interfaces and work similarly 
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_creations.xml");
		//register our shutdownhook
		context.registerShutdownHook();
		System.out.println("Demoing various kind of creations");
		drawTriangle(context);
		drawTriangle2(context);	
		drawAutoWiredByNameTriangle(context);
		drawAutoWiredByTypeTriangle(context);
		demoScope(context);
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}
	
	public static void demoSimpleCreation() {
		//simpler bean definition so that output is not cluttered.. just for checking init/default/interface destroy methods
		//demo BeanFactoryPostProcessor and PropertyPlaceholderConfigurer 
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_simple.xml");
		context.registerShutdownHook();	
		System.out.println("Demoing simple creatioons");
		System.out.println("point1 is defined with values 100, 100 in spring2.xml. But we created a new Point object in DrawingAppBeanPostProcessor"
				+ "so we will be getting a bean with values (125, 125) instead of the original values");
		Point point = (Point) context.getBean("point1");
		System.out.println("point 1 =  (" + point.getX() + "," + point.getY() + ")");		
		System.out.println("\n----------------------------------------------------------------------------------------------");		
	}
	
	public static void demoBeanFactoryPostProcessors() {
		//to demonstrate BeanFactoryPostProcessors
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring_pp.xml");
		context.registerShutdownHook();		
		System.out.println("Demoing BeanFactoryPostProcessors");
		String id = "triangle3";
		Triangle3 triangle3 = (Triangle3) context.getBean(id);
		triangle3.draw();
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}
	
	public static void drawTriangle(ApplicationContext context) {		
		String ids[] = new String[] {"triangle", "name-triangle", "alias1-triangle", "alias2-triangle"};
		for(String id:ids) {
			System.out.println("\nbuilding Triangle using '" + id + "'");
			Triangle triangle = (Triangle) context.getBean(id);
			triangle.draw();		
			System.out.println("type = " + triangle.getType());
			System.out.println("height = " + triangle.getHeight());
			System.out.println("angle = " + triangle.getAngle());
			System.out.println("pointA = (" + triangle.getPointA().getX() + "," + triangle.getPointA().getY() + ")");
			System.out.println("pointB = (" + triangle.getPointB().getX() + "," + triangle.getPointB().getY() + ")");
			System.out.println("pointC = (" + triangle.getPointC().getX() + "," + triangle.getPointC().getY() + ")");
			List<Point> points = triangle.getPoints();
			System.out.println("Printing Point list ... ");
			for (Point point:points) {
				System.out.println("(" + point.getX() + "," + point.getY() + ")");
			}
		}
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}	
	
	public static void drawTriangle2(ApplicationContext context) {		
		String ids[] = new String[] {"triangleNew", "childTriangle1", "childTriangle2", "childTriangle3"};
		for(String id:ids) {
			System.out.println("\nbuilding Triangle2 using '" + id + "'");
			Triangle2 triangle2 = (Triangle2) context.getBean(id);
			triangle2.draw();
			System.out.println("pointA = (" + triangle2.getPointA().getX() + "," + triangle2.getPointA().getY() + ")");
			System.out.println("pointB = (" + triangle2.getPointB().getX() + "," + triangle2.getPointB().getY() + ")");
			System.out.println("pointC = (" + triangle2.getPointC().getX() + "," + triangle2.getPointC().getY() + ")");	
			List<Point> points = triangle2.getPoints();
			System.out.println("Printing Point list ... ");
			for (Point point:points) {
				System.out.println("(" + point.getX() + "," + point.getY() + ")");
			}
			
		}
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}	 
	
	public static void drawAutoWiredByNameTriangle(ApplicationContext context) {
		String ids[] = new String[] {"autoWiredByNameTriangle"};
		for(String id:ids) {
			System.out.println("\nbuilding AutoWiredByNameTriangle using '" + id + "'");
			AutoWiredByNameTriangle triangle = (AutoWiredByNameTriangle) context.getBean(id);
			triangle.draw();
			System.out.println("Autowired Point are ... ");
			System.out.println("autoPointA = (" + triangle.getAutoPointA().getX() + "," + triangle.getAutoPointA().getY() + ")");
			System.out.println("autoPointB = (" + triangle.getAutoPointB().getX() + "," + triangle.getAutoPointB().getY() + ")");
			System.out.println("autoPointC = (" + triangle.getAutoPointC().getX() + "," + triangle.getAutoPointC().getY() + ")");
		}
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}
	
	public static void drawAutoWiredByTypeTriangle(ApplicationContext context) {
		String ids[] = new String[] {"autoWiredByTypeTriangle"};
		for(String id:ids) {
			System.out.println("\nbuilding AutoWiredByTypeTriangle using '" + id + "'");
			AutoWiredByTypeTriangle triangle = (AutoWiredByTypeTriangle) context.getBean(id);
			triangle.draw();
			System.out.println("Autowired Point are ... ");
			System.out.println("autoPointA = (" + triangle.getAutoPointA().getX() + "," + triangle.getAutoPointA().getY() + ")");
			System.out.println("autoPointB = (" + triangle.getAutoPointB().getX() + "," + triangle.getAutoPointB().getY() + ")");
			System.out.println("autoPointC = (" + triangle.getAutoPointC().getX() + "," + triangle.getAutoPointC().getY() + ")");
		}
		System.out.println("As can be seen above same Point is mapped to all three points.. So not a good idea if multiple types are present in the bean ... ");
		System.out.println("\n----------------------------------------------------------------------------------------------");	
	}		
	
	public static void demoScope(ApplicationContext context) {	
		System.out.println("\ngetting 'pointSingleton' which is declared with scope singleton. We will change value and it will retain the same value across differnet getBeans call");
		Point pointSingleton = (Point)context.getBean("pointSingleton");
		System.out.println("original value = " + "(" + pointSingleton.getX() + "," + pointSingleton.getY() + ")");
		System.out.println("Updating value to (1500, 1500) using setter");
		pointSingleton.setX(1500);
		pointSingleton.setY(1500);
		System.out.println("making another call to getBean and getting value in pointSingleton1");
		Point pointSingleton1 = (Point)context.getBean("pointSingleton");
		System.out.println("value in = pointSingleton1" + "(" + pointSingleton.getX() + "," + pointSingleton.getY() + ")");
		System.out.print("is pointSingleton == pointSingleton1 = " );
		System.out.println(pointSingleton == pointSingleton1);
		
		
		System.out.println("\ngetting 'pointPrototype' which is declared with scope prototype. We will change value and it will retain the same value across differnet getBeans call");
		Point pointPrototype = (Point)context.getBean("pointPrototype");
		System.out.println("original value = " + "(" + pointPrototype.getX() + "," + pointPrototype.getY() + ")");
		System.out.println("Updating value to (2500, 2500) using setter");
		pointPrototype.setX(2500);
		pointPrototype.setY(2500);
		System.out.println("making another call to getBean and getting value in pointPrototype1");
		Point pointPrototype1 = (Point)context.getBean("pointPrototype");
		System.out.println("value in = pointPrototype1" + "(" + pointPrototype1.getX() + "," + pointPrototype1.getY() + ")");
		System.out.print("is pointPrototype == pointPrototype1 = " );
		System.out.println(pointPrototype == pointPrototype1);
		System.out.println("\n----------------------------------------------------------------------------------------------");
	}
}