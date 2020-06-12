package com.renuka;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DrawingAppBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessBeforeInitialization called for " + beanName);
		// we can make any changs to bean and return the newly changed bean
		// we can actually return any object from here and spring will take it. potentially we can return totally different bean :)
		// we will check if the point name is point1 and if so we will create a new point with values 125, 125 and send that, just for fun
		if(beanName.equalsIgnoreCase("point1")) {
			bean =  new Point(125, 125);			
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessAfterInitialization called for " + beanName);
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
