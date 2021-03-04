package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.User;
import com.example.repositiories.CommentRepo;
import com.example.repositiories.CourseRepo;
import com.example.repositiories.EnrolledCourseRepo;
import com.example.repositiories.FeedbackRepo;
import com.example.repositiories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	CommentRepo comr;

	@Autowired
	UserRepo ur;

	@Autowired
	CourseRepo cr;

	@Autowired
	EnrolledCourseRepo ecr;

	@Autowired
	FeedbackRepo fr;

	@Override
	public Comment addComment(int userID, int courseID, String msg) {

		Optional<User> userD = ur.findById(userID);
		
		Optional<Course> courseD = cr.findById(courseID);
		if (ecr.findAllByUserAndCourse(userD.get(), courseD.get()).size() == 1) {
			Comment comment = new Comment(msg);
			comment.setUser(userD.get());
			comment.setCourse(courseD.get());
			return comr.save(comment);
		}
		return null;

	}

	@Override
	public boolean deleteComment(int commentid) {

		comr.deleteById(commentid);
		return true;
	}

	@Override
	public Comment updateComment(int userid, int courseid, int commentid, String comment_msg) {
		Optional<User> userD = ur.findById(userid);

		Optional<Course> courseD = cr.findById(courseid);
		if (ecr.findAllByUserAndCourse(userD.get(), courseD.get()).size() == 1) {
			Comment comment = new Comment(commentid, comment_msg);
			comment.setUser(userD.get());
			comment.setCourse(courseD.get());
			return comr.save(comment);
		}
		return null;
	}

	@Override
	public List<Comment> fetchComment(int id) {

		List<Comment> comments = comr.fetchComment(id);
		return comments;

	}

	@Override
	public Feedback addFeedback(int uid, int cid, String feedback) {
		boolean value = isCourseCompleted(cid, uid);
		if(value == true) {
			Optional<Course> c = cr.findById(cid);
			Optional<User> u = ur.findById(uid);
			Feedback fb = new Feedback(feedback, c.get(), u.get());
			// TODO Auto-generated method stub

			return fr.save(fb);
		}
		return null;
	}

	@Override
	public boolean isCourseCompleted(int cid, int uid) {
		Optional<Course> c = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(), c.get());
		if (ec.getStartDate() != null & ec.getEndDate() != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFeedback(int feedbackid) {
		fr.deleteById(feedbackid);
		return true;
	}

	@Override
	public Feedback updateFeedback(int userid, int courseid, int feedbackid, String feedback_msg) {
		Optional<User> userD = ur.findById(userid);

		Optional<Course> courseD = cr.findById(courseid);
		if (ecr.findAllByUserAndCourse(userD.get(), courseD.get()).size() == 1) {
			Feedback feedback = new Feedback(feedbackid, feedback_msg);
			feedback.setUser(userD.get());
			feedback.setCourse(courseD.get());
			return fr.save(feedback);
		}
		return null;
	}

	@Override
	public List<Feedback> fetchFeedbacks(int cid) {
		Optional<Course> c = cr.findById(cid);
		return fr.findAllByCourse(c.get());
	}

	@Override
	public List<Course> fetchcompletedCourses(int userId) {
		 Optional<User> userD = ur.findById(userId);
		 List<Course> ecrs = cr.getFinsihedCourses(userId);
		 return ecrs;
//		/ return  ecrs.stream().filter(a->a.!=null && a.getStartDate()!=null).collect(Collectors.toList());
		
	}

}
