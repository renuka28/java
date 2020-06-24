package org.renuka.learn.java.springboot.data.course;


import java.util.List;

import org.renuka.learn.java.springboot.data.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	

	@RequestMapping("/topics/{id}/courses")
	public List<Course> getAllCourses(@PathVariable("id") String id) {
		
		return courseService.getAllCourses(id);
	}
	
	@RequestMapping("/topics/{topicId}/courses/{courseId}")
	public Course getCourse(@PathVariable("topicId") String topicId, 
			@PathVariable("courseId") String courseId) {
		
		return courseService.getCourse(courseId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses")
	public void addCourse(@PathVariable("topicId") String topicId, 
			@RequestBody Course course) {
		course.setTopic(new Topic(topicId, "", ""));
		courseService.addCourse(course);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{courseId}")
	public void updateCourse(@RequestBody Course course, @PathVariable("topicId") String topicId, 
			@PathVariable("courseId") String courseId) {
		course.setTopic(new Topic(topicId, "", ""));
		courseService.updateCourse(course);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{courseId}")
	public void deleteCourse(@PathVariable("courseId") String courseId) {
		
		courseService.deleteCourse(courseId);
	}
	
}
