package com.renuka;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CircleMessageSource implements Shape{
	
	private SimplePoint center;

	//spring will inject this at run time. We will use xml to tie it up with a MessageSource bean
	// we can also implement ApplicationContextAware interface, get application context and use getMessage on that
	//there is only one bean so we can autowire by type
	@Autowired 
	private MessageSource messageSource;
	
	
	public MessageSource getMessageSource() {
		return messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	
	public SimplePoint getCenter() {
		return center;
	}


	@Autowired
	@Qualifier("myCirclePoint")
	public void setCenter(SimplePoint center) {
		this.center = center;
	}


	@Override
	public void draw() {
		System.out.println(messageSource.getMessage("CircleMessageSourceDrawMessage", null, "Default hello", null));
		System.out.println(messageSource.getMessage("drawing.point.text", new Object[] {center.getX(), center.getY()},
				"drawing.point.text text not found", null));
		System.out.println("CircleMessageSource - message from property file = " + messageSource.getMessage("greeting", null, "Default hello", null));
		
	} 
	
}
 
