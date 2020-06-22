package org.renuka.learn.java.springboot.starter.topic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topics.add(new Topic("first", "first spring", "first great spring app"));
		topics.add(new Topic("second", "second spring", "second great spring app"));
		
		
		return topics;
	}
}
