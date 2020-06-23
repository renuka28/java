package org.renuka.learn.java.springboot.starter.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class TopicService {

	private List<Topic> topics = new ArrayList<> (Arrays.asList(
			new Topic("java", "Java Spring Boot", "Java Spring boot is great. Everyone should learn"),
			new Topic("python", "Python for Machine Learning", "Python is great for ML and AI. You should learn")
			));
	
	public List<Topic> getAllTopics() {
		return topics;
	}
	
	public Topic getTopic(String id) {
		try {
			return topics.stream().filter(t -> t.getId().equals(id.toLowerCase())).findFirst().get();
		}catch(NoSuchElementException ex) {
			return new Topic(id, "NOT A VALID ID - NO SUCH TOPIC EXIST", "NOT A VALID ID - DESCRIPTION NOT FOUND");
		}
		
	}

	public void addTopic(Topic topic) {
		topics.add(topic);
		
	}

	public void updateTopic(String id, Topic topic) {
		for (int i =0; i < topics.size(); i++){
			Topic t = topics.get(i);
			if(t.getId().equals(id)) {
				topics.set(i, topic);		
				return;
			}
		}
		
	}
	
}
