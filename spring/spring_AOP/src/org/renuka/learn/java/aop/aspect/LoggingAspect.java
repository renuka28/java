package org.renuka.learn.java.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
	
	@Around("execution(* get*(..))")
	public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		
		//this call will ensure the method that is interrupted wil get executed
		// we can choose to do so and it is not compulsory 
		Object returnValue = null;
		try {
			System.out.println("myAroundAdvice - before calling");
			returnValue = proceedingJoinPoint.proceed();
			System.out.println("myAroundAdvice - after calling");			
		}catch (Throwable e) {
			System.out.println("After Throwing");
		}
		System.out.println("After finally");
		return returnValue;
	}

//	//only be called if the target method raised any exception
//	@AfterThrowing(pointcut="args(name)", throwing="ex")
//	public void stringArgumentMethodsAfterExceptionAdvice(String name, Exception ex) {		
//		System.out.println("@AfterThrowing method with a single String argument called. value passed = '" + name +
//				"'. exception value = '" + ex.getStackTrace() +"'");
//	}
	
//	//only be called if the target method exectued without any exception and get hold of the return value
//	@AfterReturning(pointcut="args(name)", returning="retVal" )
//	public void stringArgumentMethodsAfterReturningWithReturnValueAdvice(String name, String retVal) {		
//		System.out.println("AfterReturing method with a single String argument called. value passed = '" + name +
//				"'. return value = '" + retVal +"'");
//	}
	
	
//	@Before("args(name)")
//	public void stringArgumentMethodsBeforeAdvice(String name) {		
//		System.out.println("Before method with a single String argument called. value passed = " + name);
//	}
//	
//	//will be executed irrespective of whether the method throws an exception or not
//	@After("args(name)")
//	public void stringArgumentMethodsAfterAdvice(String name) {		
//		System.out.println("After method with a single String argument called. value passed = " + name);
//	}
//	
//	//only be called if the target method exectued without any exception
//	@AfterReturning("args(name)")
//	public void stringArgumentMethodsAfterReturningAdvice(String name) {		
//		System.out.println("AfterReturing method with a single String argument called. value passed = " + name);
//		
//	}	

//	
//	//only be called if the target method raised any exception
//	@AfterThrowing("args(name)")
//	public void stringArgumentMethodsAfterExceptionAdvice(String name) {		
//		System.out.println("@AfterThrowing method with a single String argument called. value passed = " + name);
//	}
	
	
//	//aspect with args.. we just specifiy the name of the argument and all methods with the type of the argument will be get this
//	@Before("args(name)")
//	public void stringArgumentMethodsAdvice(String name) {		
//		System.out.println("value passed = " + name);
//	}
//	
	
//	
//	@Before("allCirclePointcut()")
//	public void LoggingAdviceJoinPointAdvice(JoinPoint joinPoint) {
//		
//		System.out.println(joinPoint.toString());
//		System.out.println(joinPoint.getTarget());
//	}
//	
//	@Before("args(String)")
//	public void stringArgumentMethodsAdvice() {
//		System.out.println("method with string arugement is called");
//	}
//
//	
//	
//	@Before("allGettersPointcut() && allCirclePointcut()")
//	public void LoggingAdviceBeforeAllgettersAndAllCircleAdvice() {
//		
//		System.out.println("LoggingAdviceBeforeAllgettersAndAllCircle - within(org.renuka.learn.java.aop..*)- Advice run.");
//	}
//	
//	@Before("allCirclePointcut3()")
//	public void LoggingAdviceBeforeAopPackageMethodsWithiAdvicen() {
//		
//		System.out.println("LoggingAdviceBeforeAopPackageMethodsWithin - within(org.renuka.learn.java.aop..*)- Advice run.");
//	}
//	
//	
//	@Before("allCirclePointcut2()")
//	public void LoggingAdviceBeforeAllCircleMethodsExecutionAdvice() {
//		System.out.println("LoggingAdviceBeforeAllCircleMethodsWithin -execution(* org.renuka.learn.java.aop.model.Circle.*(..))- Advice run.");
//	}
//	
//	@Before("allCirclePointcut()")
//	public void LoggingAdviceBeforeAllCircleMethodsWithinAdvice() {
//		System.out.println("LoggingAdviceBeforeAllCircleMethodsWithin - within(org.renuka.learn.java.aop.model.Circle)- Advice run.");
//	}
//	
//	
//	
//	@Before("allCirclePointcut()")
//	public void LoggingAdviceBeforeAllCircleMethodsAdvice() {
//		System.out.println("LoggingAdviceBeforeAllCircleMethods - * * org.renuka.learn.java.aop.model.Circle.*(..)) - Advice run.");
//	}
//	
//	
//
//	@Before("allGettersPointcut()")
//	public void LoggingAdviceBeforeStarGetStar3Advice() {
//		System.out.println("LoggingAdviceBeforeStarGetStar3 - * get*(..)- - Advice run.");
//	}
//	
//	@Before("allGettersPointcut()")
//	public void LoggingAdviceBeforeStarGetStar2Advice() {
//		System.out.println("LoggingAdviceBeforeStarGetStar2 - * get*(..)- - Advice run.");
//	}
//	
//	@Before("allGettersPointcut()")
//	public void LoggingAdviceBeforeStarGetStarAdvice() {
//		System.out.println("LoggingAdviceBeforeStarGetStar - * get*(..)- - Advice run.");
//	}
//	
//	@Before("execution(public int get*())")
//	public void LoggingAdviceBeforeIntGetStarAdvice() {
//		System.out.println("LoggingAdviceBeforeStarGetStar - public int get*()- - Advice run.");
//	}
//	
//	
//	@Before("execution(public String get*())")
//	public void LoggingAdviceBeforeStringGetWildStarAdvice() {
//		System.out.println("LoggingAdviceBeforeStringGetWildStar - public String get*()- - Advice run.");
//	}
//	
//	@Before("execution(public String getName())")
//	public void LoggingAdviceBeforeStringGetNameAdvice() {
//		System.out.println("LoggingAdviceBeforeStringGetName - public String getName() - Advice run.");
//	}
//	
//	@Before("execution(public String org.renuka.learn.java.aop.model.Circle.getName())")
//	public void LoggingAdviceBeforStringCircleGetNameAdvice() {
//		System.out.println("LoggingAdviceBeforStringCircleGetName - public String org.renuka.learn.java.aop.model.Circle.getName() - Advice run.");
//	}
	
	
	@Pointcut("args(name)")
	public void allMethodsWithOneStringArg() {}
	
	@Pointcut("execution(* get*(..))")
	public void allGettersPointcut() {}
	
	@Pointcut("within(org.renuka.learn.java.aop.model.Circle)")
	public void allCirclePointcut() {}	
	
	@Pointcut("execution(* org.renuka.learn.java.aop.model.Circle.*(..))")
	public void allCirclePointcut2() {}
	
	@Pointcut("within(org.renuka.learn.java.aop..*)")
	public void allCirclePointcut3() {}
	
	
//	@After("execution(public String getName())")
//	public void LoggingAdviceAfter() {
//		System.out.println("Advice run. Get Method completed");
//	}

}
