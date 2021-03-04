package com.example.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.User;

public interface EnrolledCourseRepo extends JpaRepository<EnrolledCourses, Integer> {
	public List<EnrolledCourses> findAllByUserAndCourse(User u,Course c);
	public EnrolledCourses findByUserAndCourse(User user, Course course);
	public List<EnrolledCourses> findAllByUser(User u);

 }
