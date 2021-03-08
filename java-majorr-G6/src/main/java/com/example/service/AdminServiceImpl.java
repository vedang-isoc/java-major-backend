package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.EnrolledCourses;
import com.example.repositiories.CourseRepo;

public class AdminServiceImpl implements AdminService{
	@Autowired
	CourseRepo cr;
	@Autowired
	EnrolledCourses ecr;
	@Override
	public List<List<String>> getReport(List<String> keywords) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
