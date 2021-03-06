package com.example.service;

import java.util.List;

import com.example.entity.Course;
import com.example.entity.Profile;
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
	public int incrementfailed(String username);
	public boolean clearfalied(int uid);
	public boolean isLocked(String username);
	public abstract String createUser(User user);
	public boolean isActivated(int uid);
	public abstract Profile createProfile(int userid, Profile profile);
	public boolean isProfileCreated(int uid);
	public abstract boolean checkUsername(String username);
	public abstract boolean checkEmail(String email);
	public boolean checkPwd(String password,int uid);
	public abstract boolean deleteUser(int id);
	public List<Course> findCourseByCat(int catid);

}
