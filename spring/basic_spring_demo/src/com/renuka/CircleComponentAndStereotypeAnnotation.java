package com.renuka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


/*
 * declaring @Component is equal to 
<bean id="circleComponentAndStereotypeAnnotation" class="com.renuka.CircleComponentAndStereotypeAnnotation">
</bean>
Adding annotations results in typing the internal data (SImplePoint) to only one object.. Meaning
that we cannot change what point it is referring to using annotations. If we use xml we can change it

annotations automatically names this component as circleComponentAndStereotypeAnnotation. that is changes the first letter to lower case
we can also use 
@Service - for services beans
@Repository- for data beans
@Controller - MVC Controller

@COmponent is a generic annotations and other are more specific and provides more information to string on the bean's expected behaviour

at the basic level all are @Component which is a plain old bean
*/

//@Controller
//@Service
//@Repository
@Component
public class CircleComponentAndStereotypeAnnotation implements Shape{
	
	private SimplePoint center;
	
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
		System.out.println("CircleComponentAndStereotypeAnnotationd with Shape Interface drawn from ApplicationContext");
		System.out.print("center = (" + center.getX() + "," + center.getY() + ")");	
	}

}
 
