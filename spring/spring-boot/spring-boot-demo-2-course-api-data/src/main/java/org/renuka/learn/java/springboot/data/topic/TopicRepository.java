package org.renuka.learn.java.springboot.data.topic;

import org.springframework.data.repository.CrudRepository;

//Topic entity with a String id
public interface TopicRepository extends CrudRepository<Topic, String> {
	
	

}
