package org.renuka.learn.java.springboot.data.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	
	public List<Course> getAllCourses(String id) {
		System.out.println("getting all topics using CourseRepository for id - " + id);
		List<Course> courses = new ArrayList<>();		
		courseRepository.findByTopicId(id).forEach(courses::add);
		return courses;
	}
	
	public Course getCourse(String id) {
		System.out.println("getting Courses using CourseRepository- " +id);
		return courseRepository.findById(id).get();
		
	}

	public void addCourse(Course course) {
		System.out.println("adding Course - using CourseRepository " +course.getId());
		courseRepository.save(course);		
		
	}

	public void updateCourse( Course course) {
		System.out.println("updating Course using CourseRepository with values - " + course);
		courseRepository.save(course);
		
		
	}

	public void deleteCourse(String id) {
		System.out.println("deleting Course  using CourseRepository- " +id);
		courseRepository.deleteById(id);
	}
	
}
