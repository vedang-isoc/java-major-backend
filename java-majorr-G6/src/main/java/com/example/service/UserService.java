package com.example.service;

import java.util.List;

import com.example.entity.Course;
import com.example.entity.User;
import com.example.entity.Video;

public interface UserService {
	public boolean like(int uid,int cid);
	public boolean unlike(int likeid,int cid);
	public List<Course> getCourses();
	public boolean isCourseCompleted(int cid,int uid);
	public boolean addFeedback(String feedback,int uid,int cid);
	public List<Course> getEnrolledCourse(int uid);
	public List<Video> getEnrolledCourseVideo(int uid,int cid);
	public boolean lockAccount(int uid);
	public boolean unlocakAccount(int uid);
	public List<User> getLockedAccount();
	public boolean generateCompeletionCerti(int uid,int cid);
	public boolean Enroll(int cid,int uid);
	public boolean nextVideo(int cid,int uid,int vid);
	public boolean completeVideo(int cid,int uid,int vid);

}
