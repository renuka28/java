package org.renuka.learn.java.aop.service;

import org.renuka.learn.java.aop.aspect.LoggingAspectCustom;
import org.renuka.learn.java.aop.model.Circle;

public class ShapeServiceProxy extends ShapeService {
	
	public Circle getCircle() {
		new LoggingAspectCustom().loggingAdvice();
		return super.getCircle();
	}
}
