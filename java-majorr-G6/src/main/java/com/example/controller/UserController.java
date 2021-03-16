package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;

import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Course;
import com.example.entity.Profile;
import com.example.entity.User;
import com.example.entity.Video;
import com.example.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")

public class UserController {
	
	@Autowired
	UserService uservice;
	
	@PutMapping(path="/{cid}")
	public String like(@PathVariable int cid) {
		
		//11 is the user id
		boolean status=uservice.like(4, cid);
		if(status) {
			return "Liked";
		}
		return "can not like";
	}
	@DeleteMapping(path="/{likeid}/{cid}")
	public String unlike(@PathVariable int likeid,@PathVariable int cid) {
		
		//11 is the user id
		boolean status=uservice.unlike(likeid,cid);
		if(status) {
			return "Un Liked";
		}
		return "can not unlike";	}
	@GetMapping(path="/courses")
	public List<Course> getcourses(){
		return uservice.getCourses();
	}
	@GetMapping(path="/isCourseCompleted/{cid}")
	public boolean test(@PathVariable int cid) {
		return uservice.isCourseCompleted(112,cid);
		
	}
	@GetMapping(path="/enrolledcourses")
	public List<Course> getecourses(){
		return uservice.getEnrolledCourse(4);
	}
	@GetMapping(path="/enrolledcourses/video/{cid}/{uid}")
	public List<Video> getVideo(@PathVariable int cid,@PathVariable int uid){
		return uservice.getEnrolledCourseVideo(uid,cid);
	}
	@GetMapping(path="/lockedusers")
	public List<User> getLocked(){
		return uservice.getLockedAccount();
	}
	@PutMapping(path="/unlockuser")
	public boolean unlock(){
		return uservice.unlocakAccount(4); 
	}
	@PutMapping(path="/lockuser")
	public boolean lock(){
		return uservice.lockAccount(4);
	}
	@GetMapping(path="/generatePdf/{cid}")
	public boolean generatepdf(@PathVariable int cid){
		return uservice.generateCompeletionCerti(100, cid);
	}
	@PutMapping(path="enroll/{cid}/{uid}")
	public String enroll(@PathVariable int cid,@PathVariable int uid) {
		
		//11 is the user id
		boolean status=uservice.Enroll(cid, uid);
		if(status) {
			return "Enrolled";
		}
		return "can not enroll";
	}
	
	@GetMapping(path="nextvideo/{cid}/{uid}/{vid}")
	public boolean nextVideo(@PathVariable int cid,@PathVariable int uid,@PathVariable int vid) {
		
		
		return uservice.nextVideo(cid, uid, vid);
	}
	@PutMapping(path="completevideo/{cid}/{uid}/{vid}")
	public boolean completeVideo(@PathVariable int cid,@PathVariable int uid,@PathVariable int vid) {
		
		
		return uservice.completeVideo(cid, uid, vid);
	}
	@PutMapping("/incrementfa/{username}")
	public boolean ifa(@PathVariable String username) {
		return uservice.incrementfailed(username);
	}
    
	@PutMapping("/clearfa/{userid}")
	public boolean cfa(@PathVariable int userid) {
		return uservice.clearfalied(userid);
		
	}
	@GetMapping("/isLocked/{username}")
	public boolean isLocked(@PathVariable String username) {
		return uservice.isLocked(username);
		
	}
	@PostMapping(path="/adduser")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createUser(@RequestBody User user) {
		return uservice.createUser(user);
	}
	@GetMapping(path="isActivated/{userid}")
	public boolean isActivated(@PathVariable int userid) {
		return uservice.isActivated(userid);
	}
	@PostMapping("/profile/{userid}") //we have send userid while creating profile
	@ResponseStatus(code = HttpStatus.CREATED)
	public Profile createProfile(@PathVariable int userid,@RequestBody Profile profile,@RequestParam("myFile") MultipartFile file) {
		//file from model attribute 
		
		//profile.setUserImage(file.getInputStream());
		try {
			InputStream inputStream = file.getInputStream();
			byte[] b = IOUtils.toByteArray(inputStream);
			SerialBlob blob = new javax.sql.rowset.serial.SerialBlob(b);
			profile.setUserImage(blob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uservice.createProfile(userid, profile);
	}
	@GetMapping("/isProfileCreated/{userid}")
	public boolean isPC(@PathVariable int userid) {
		return uservice.isProfileCreated(userid);
	}
	

	

}
