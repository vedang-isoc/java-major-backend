package com.example.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Course;
import com.example.entity.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
	@Query(value = "SELECT * FROM feedback where course_id=?1", nativeQuery = true)
	public List<Feedback> fetchfeedback(int id);
	
	public List<Feedback> findAllByCourse(Course c);
}
