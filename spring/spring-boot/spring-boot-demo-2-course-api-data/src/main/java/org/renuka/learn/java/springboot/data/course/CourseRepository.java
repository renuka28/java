package org.renuka.learn.java.springboot.data.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//Topic entity with a String id
public interface CourseRepository extends CrudRepository<Course, String> {
	
	//this is a special case where spring can provide implementation for some of the filter methods
	//we just need to declare a method in the format findBy<PropertyName> that receives appropriate type of input
	//for eg, here we want to find a course by one of its property name. Then we can define a method like below
	//and spring will provide the actual implementation. We dont have to implement it!!!
	public List<Course> findByName(String name);
	
	//following by the same logic, if we want to get all values bases on id of one of the embedded object
	// then follow the format getBy<object_property_name><fieldin that property>
	public List<Course> findByTopicId(String topicId);

}
