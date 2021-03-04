package com.example.service;

import java.util.List;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;

public interface UserService {
	public Comment addComment(int userID,int courseID,String msg);
	public boolean deleteComment(int commentid);
	public Comment updateComment(int userid, int courseid, int commentid, String comment_msg);
	public List<Comment> fetchComment(int id);
	boolean isCourseCompleted(int cid, int uid);
	public Feedback addFeedback(int uid, int cid,String feedback);
	public boolean deleteFeedback(int feedbackid);
	public Feedback updateFeedback(int userid, int courseid, int feedbackid, String feedback_msg);
	public List<Feedback> fetchFeedbacks(int id);
	public List<Course> fetchcompletedCourses(int userId);
	
	
}
