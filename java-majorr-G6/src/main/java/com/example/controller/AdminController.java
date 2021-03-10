package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Course;
import com.example.service.AdminService;
import com.example.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService aservice;
	
	
	@GetMapping(path="coursereports/{cid}")
	public List<Course> courseReports(@PathVariable int cid){
		return aservice.courseStats(cid);
		
	}

}
