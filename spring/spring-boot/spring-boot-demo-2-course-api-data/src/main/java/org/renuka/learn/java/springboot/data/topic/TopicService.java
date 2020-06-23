package org.renuka.learn.java.springboot.data.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	
	public List<Topic> getAllTopics() {
		System.out.println("getting all topics using TopicRepository...");
		List<Topic> topics = new ArrayList<>();		
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	public Topic getTopic(String id) {
		System.out.println("getting topic using TopicRepository- " +id);
		return topicRepository.findById(id).get();
		
	}

	public void addTopic(Topic topic) {
		System.out.println("adding topic - using TopicRepository " +topic.getId());
		topicRepository.save(topic);		
		
	}

	public void updateTopic(String id, Topic topic) {
		System.out.println("updating topic using TopicRepository - " +id + " with values " + topic);
		topicRepository.save(topic);
		
		
	}

	public void deleteTopic(String id) {
		System.out.println("deleting topic  using TopicRepository- " +id);
		topicRepository.deleteById(id);
	}
	
}
