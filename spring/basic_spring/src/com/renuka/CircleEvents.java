package com.renuka;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CircleEvents implements Shape, ApplicationEventPublisherAware{
	
	private SimplePoint center;
	private ApplicationEventPublisher circleEventsPublisher;
	
	

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		// TODO Auto-generated method stub
		circleEventsPublisher = publisher;		
	}


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
		System.out.println(messageSource.getMessage("draw.message", new Object[] {"CircleEvents"}, "CircleEvents draw method called - static text", null));
		System.out.println(messageSource.getMessage("drawing.point.text", new Object[] {center.getX(), center.getY()},
				"drawing.point.text text not found", null));
		DrawEvent drawEvent = new DrawEvent(this);
		circleEventsPublisher.publishEvent(drawEvent);
		
	} 
	
}
 
