package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.repositiories.CommentRepo;
import com.example.repositiories.CourseRepo;
import com.example.repositiories.EnrolledCourseRepo;
import com.example.repositiories.FeedbackRepo;

public class AdminServiceImpl implements AdminService{
	@Autowired
	CourseRepo cr;
	@Autowired
	EnrolledCourseRepo ecr;
	@Autowired
	CommentRepo cmtr;
	@Autowired
	FeedbackRepo fr;
	@Override
	public List<Course> courseStats(int cid) {
		// TODO Auto-generated method stub
		List<Course> courses=cr.findAll();
		List<Comment> comments=cmtr.findAll();
		List<Feedback> feedbacks=fr.findAll();
		List<EnrolledCourses> ecs=ecr.findAll();
		for (int i=0;i<courses.size();i++) {
			for(int j=0;j<comments.size();j++) {
				if(courses.get(i).getCourseId()==comments.get(j).getCourse().getCourseId() ) {
					courses.get(i).setTotalcomment(courses.get(i).getTotalcomment()+1);
					
				}
			}
			float avgrating = 0;
			for (Feedback f :feedbacks) {
				avgrating = avgrating + f.getRatings();
			}
			int rating = (int) (avgrating / (courses.get(i).getFeedbacks().size()));
			courses.get(i).setAvgrating(rating);
			for(int l=0;l<ecs.size();l++) {
				if(courses.get(i).getCourseId()==ecs.get(l).getCourse().getCourseId() ) {
					courses.get(i).setEnrollments(courses.get(i).getEnrollments()+1);
					System.out.println(courses.get(i).getTotalcomment());
				}
			}
			
			
			
		}
		return courses;
	}

	
	

}
