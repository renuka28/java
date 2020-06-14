package org.renuka.learn.java.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

//this class has not annotations. All aspect configuration is done in xml
public class LoggingXMLAspect {
	
	public Object myXMLAdvice(ProceedingJoinPoint proceedingJoinPoint) {
	
		//this call will ensure the method that is interrupted wil get executed
		// we can choose to do so and it is not compulsory 
		Object returnValue = null;
		try {
			System.out.println("myXMLAdvice - org.renuka.learn.java.aop.aspect.LoggingXMLAspect - before calling");
			returnValue = proceedingJoinPoint.proceed();
			System.out.println("myXMLAdvice - org.renuka.learn.java.aop.aspect.LoggingXMLAspect - after calling");			
		}catch (Throwable e) {
			System.out.println("myXMLAdvice - org.renuka.learn.java.aop.aspect.LoggingXMLAspect - After Throwing");
		}
		System.out.println("myXMLAdvice - org.renuka.learn.java.aop.aspect.LoggingXMLAspect - After finally");
		return returnValue;
	}	
	
	public void myXMLSetAdviceBefore() {
		System.out.println("myXMLSetAdviceBefore - - Advice run.");
	}
	
	public void myXMLSetAdviceAfter() {
		System.out.println("myXMLSetAdviceAfter- - Advice run.");
	}
}
