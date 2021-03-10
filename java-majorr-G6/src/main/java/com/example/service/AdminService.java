package com.example.service;

import java.util.List;

import com.example.entity.Course;

public interface AdminService {
	
	public List<Course> courseStats(int cid);

}
