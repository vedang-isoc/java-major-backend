package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.Video;

public interface UserService {
	public Comment addComment(int userID,int courseID,String msg);
	public boolean deleteComment(int commentid);
	public Comment updateComment(int userid, int courseid, int commentid, String comment_msg);
	public List<Comment> fetchComment(int id);
	boolean isCourseCompleted(int cid, int uid);
	public Feedback addFeedback(int uid, int cid,String feedback,int rating);
	public boolean deleteFeedback(int feedbackid);
	public Feedback updateFeedback(int userid, int courseid, int feedbackid, String feedback_msg);
	public List<Feedback> fetchFeedbacks(int id);
	public List<Course> fetchcompletedCourses(int userId);
	public List<Course> AllCourse();
	public List<Course> setAvgRating(List<Course> courses);
	public Optional<Course> getCourseById(int courseid);
	Optional<Course> setAvgRating(Optional<Course> c);
	public int feedbackcount(int courseid);
	public int commentcount(int courseid);
	public List<Video> getVideos(int courseId);
	public boolean like(int i, int cid);
	public boolean unlike(int likeid, int cid);
	public boolean isliked(int cid,int uid);
	public boolean Enroll(int cid, int uid);
	public boolean isenrolled(int cid, int uid);
	List<Course> getEnrolledCourse(int uid);
	List<Video> getEnrolledCourseVideo(int uid, int cid);
	boolean nextVideo(int cid, int uid, int vid);
	boolean completeVideo(int cid, int uid, int vid);
	boolean generateCompeletionCerti(int uid, int cid);
}
