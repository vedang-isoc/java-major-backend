package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.Video;

public interface AdminService {
	public List<Category> getAllCategory();

	public Optional<Category> getCategoryById(int id);

	public boolean addCategory(Category c);

	public void deleteCategory(int id);

	public boolean updateCategory(Category c);

	public List<Course> getAllCourse();

	public Optional<Course> getCourseById(int id);

	public boolean addCourse(Course c, int id);

	public void deleteCourse(int i);

	public boolean updateCourse(Course c);

	public List<Video> getAllVideo();

	public Optional<Video> getVideoById(int id);

	public boolean addVideo(Video v);

	public void deleteVideo(int i);

	boolean updateVideo(Video v);
	
	
}
