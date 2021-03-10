package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.Video;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.core.ipc.http.HttpSender.Request;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

	@Autowired
	UserService uservice = new UserServiceImpl();

	@PostMapping("/comment")
	public ResponseEntity<String> postComment(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam String comment_msg) {
		Comment count = uservice.addComment(userid, courseid, comment_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add comment");
		}
		return ResponseEntity.status(HttpStatus.OK).body("comment added");

	}

	@PostMapping("/feedback")
	public ResponseEntity<String> postfeedback(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam String feedback_msg, @RequestParam int rating) {
		Feedback count = uservice.addFeedback(userid, courseid, feedback_msg, rating);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add feedback");
		}
		return ResponseEntity.status(HttpStatus.OK).body("feedback added");

	}

	@DeleteMapping("/comment")
	public ResponseEntity<String> deleteComment(@RequestParam int commentid) {

		uservice.deleteComment(commentid);
		return null;

	}

	@DeleteMapping("/feedback")
	public ResponseEntity<String> deletefeedback(@RequestParam int feedbackid) {

		uservice.deleteFeedback(feedbackid);
		return null;

	}

	@PutMapping("/comment")
	public ResponseEntity<String> upateComment(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam int commentid, @RequestParam String comment_msg) {
		Comment count = uservice.updateComment(userid, courseid, commentid, comment_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add comment");
		}
		return ResponseEntity.status(HttpStatus.OK).body("comment added");
	}

	@PutMapping("/feedback")
	public ResponseEntity<String> upatefeedback(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam int feedbackid, @RequestParam String feedback_msg) {
		Feedback count = uservice.updateFeedback(userid, courseid, feedbackid, feedback_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add feedback");
		}
		return ResponseEntity.status(HttpStatus.OK).body("feedback added");
	}

	@GetMapping("/comment")
	public List<Comment> fetchComment(@RequestParam int courseid) {
		return uservice.fetchComment(courseid);
	}

//	@GetMapping("/feedback")
//	public List<Comment> fetchFeedback(@RequestParam int feedbackid){
//		return uservice.fetchComment(feedbackid);
//	}
//	
	@GetMapping("/feedback")
	public List<Feedback> fetchFeedbackbyCourseID(@RequestParam int courseid) {
		return uservice.fetchFeedbacks(courseid);
	}

	@GetMapping("/completedCourses")
	public List<Course> completedCourses(@RequestParam int userId) {
		return uservice.fetchcompletedCourses(userId);
	}

	@GetMapping("/courses")
	public ResponseEntity<List<Course>> AllCourse() {
		List<Course> li = uservice.AllCourse();
		for (Course l : li) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	@GetMapping("/videos")
	public ResponseEntity<List<Video>> getVideos(@RequestParam int courseid) {
		List<Video> li = uservice.getVideos(courseid);
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	// show course by id
	@GetMapping("/course")
	public Optional<Course> CourseById(@RequestParam int courseid) {
		return uservice.getCourseById(courseid);
	}

	// total number of feedbacks
	@GetMapping("/feedbackcount")
	public int feedbackcount(@RequestParam int courseid) {
		return uservice.feedbackcount(courseid);
	}

	// total number of comments
	@GetMapping("/commentcount")
	public int commentcount(@RequestParam int courseid) {
		return uservice.commentcount(courseid);
	}

	@PutMapping(path = "/like/{cid}/{uid}")
	public String like(@PathVariable int cid, @PathVariable int uid) {

		// 11 is the user id
		boolean status = uservice.like(uid, cid);
		if (status) {
			return "Liked";
		}
		return "can not like";
	}

	@DeleteMapping(path = "/unlike/{cid}/{uid}")
	public String unlike(@PathVariable int cid, @PathVariable int uid) {

		// 11 is the user id
		boolean status = uservice.unlike(uid, cid);
		if (status) {
			return "Un Liked";
		}
		return "can not unlike";
	}
	
	@GetMapping("/isliked/{cid}/{uid}")
	public boolean isliked(@PathVariable int cid, @PathVariable int uid){
		
		return uservice.isliked(cid, uid);
	
	}
	
	@GetMapping("/isenrolled/{cid}/{uid}")
	public boolean isenrolled(@PathVariable int cid, @PathVariable int uid){
		
		return uservice.isenrolled(cid, uid);
	
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
	
	@GetMapping(path="/enrolledcourses/video/{cid}/{uid}")
	public List<Video> getVideo(@PathVariable int cid,@PathVariable int uid){
		return uservice.getEnrolledCourseVideo(uid,cid);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path="nextvideo/{cid}/{uid}/{vid}")
	public boolean nextVideo(@PathVariable int cid,@PathVariable int uid,@PathVariable int vid) {
		
		
		return uservice.nextVideo(cid, uid, vid);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping(path="completevideo/{cid}/{uid}/{vid}")
	public boolean completeVideo(@PathVariable int cid,@PathVariable int uid,@PathVariable int vid) {
		
		
		return uservice.completeVideo(cid, uid, vid);
	}
	
	@GetMapping(path="iscompleted/{cid}/{uid}")
	public boolean completedcourse(@PathVariable int cid,@PathVariable int uid) {
		return uservice.isCourseCompleted(cid, uid);
	}
	
	@GetMapping(path="/generatePdf/{cid}/{uid}")
	public boolean generatepdf(@PathVariable int cid,@PathVariable int uid){
		return uservice.generateCompeletionCerti(uid, cid);
	}

}
