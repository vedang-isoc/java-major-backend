package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repositiories.CategoryRepository;
import com.example.repositiories.CourseRepository;
import com.example.repositiories.VideoRepository;
import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.Video;

@Service
public class AdminServiceImpl implements AdminService{
	
	

	@Autowired
	CategoryRepository cat;
	@Autowired
	CourseRepository cou;
	@Autowired
	VideoRepository vr;

	@Override
	public List<Category> getAllCategory() {
		return cat.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(int id) {
		return cat.findById(id);
	}

	@Override
	public boolean addCategory(Category c) {
		return cat.save(c) != null;
	}

	@Override
	public void deleteCategory(int id) {
		cat.deleteById(id);

	}

	@Override
	public boolean updateCategory(Category c) {
		return cat.save(c) != null;
	}

	@Override
	public List<Course> getAllCourse() {
		return cou.findAll();
	}

	@Override
	public Optional<Course> getCourseById(int id) {
		return cou.findById(id);
	}

	@Override
	public boolean addCourse(Course c, int id) {
		Optional<Category> cate=cat.findById(id);
		List<Course> courses =cate.get().getCourses();
		courses.add(c);
		cate.get().setCourses(courses);
		cat.save(cate.get());
		return true;
	}

	@Override
	public void deleteCourse(int i) {
		cou.deleteById(i);

	}

	@Override
	public boolean updateCourse(Course c) {
		return cou.save(c) != null;
	}
	
	@Override
	public boolean addVideo(Video v) {

		return vr.save(v) != null;
	}

	@Override
	public List<Video> getAllVideo() {
		return vr.findAll();
	}

	@Override
	public Optional<Video> getVideoById(int id) {
		return vr.findById(id);
	}

	@Override
	public boolean updateVideo(Video v) {

		return vr.save(v) != null;
	}

	@Override
	public void deleteVideo(int i) {
		vr.deleteById(i);

	}

	@Override
	public long getCategoryCount() {
		return cat.count();
	}
	
	
	
	@Override
	public long getCourseCount() {
		return cou.count();
	}
	
	
	@Override
	public long getVideoCount() {
		return cou.count();
	}

}
