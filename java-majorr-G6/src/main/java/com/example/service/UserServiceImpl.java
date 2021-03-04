package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.Like;
import com.example.entity.User;
import com.example.entity.Video;
import com.example.repositiories.CourseRepo;
import com.example.repositiories.EnrolledCourseRepo;
import com.example.repositiories.FeedbackRepo;
import com.example.repositiories.LikeRepo;
import com.example.repositiories.UserRepo;
import com.example.repositiories.VideoRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	VideoRepo vr;
	@Autowired
	LikeRepo lr;
	@Autowired 
	UserRepo ur;
	@Autowired
	CourseRepo cr;
	@Autowired
	EnrolledCourseRepo ecr;
	@Autowired
	FeedbackRepo fr;

	@Override
	public boolean like(int uid, int cid) {
		
		Optional<Course> c=cr.findById(cid);
		Optional<User> u=ur.findById(uid);
		Like IfLike = lr.findByCourseAndUser(c.get(), u.get());
		if(IfLike==null) {
			Like like=new Like(c.get(),u.get());
			int likes=c.get().getLikes();c.get().setLikes(++likes);
			cr.save(c.get());
			lr.save(like);
			return true;
		}
		
		return false;


	}

	@Override
	public boolean unlike(int likeid,int cid) {
		
		Optional<Like> IfLike=lr.findById(likeid);
		if(IfLike.get()!=null) {
			Optional<Course> c=cr.findById(cid);
			int likes=c.get().getLikes();c.get().setLikes(--likes);
			cr.save(c.get());
			lr.deleteById(likeid);
			
			return true;
			
		}
		return false;
		
	}

	@Override
	public List<Course> getCourses() {
		// TODO Auto-generated method stub
		return cr.findAll();
	}

	@Override
	public boolean isCourseCompleted(int cid, int uid) {
		// TODO Auto-generated method stub
		Optional<Course> c=cr.findById(cid);
		Optional<User> u=ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(),c.get());
		if(ec.getStartDate()!=null & ec.getEndDate()!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addFeedback(String feedback, int uid, int cid) {
		
		Optional<Course> c=cr.findById(cid);
		Optional<User> u=ur.findById(uid);
		Feedback fb=new Feedback(feedback, c.get(), u.get());
		// TODO Auto-generated method stub
		fr.save(fb);
		return false;
	}

	@Override
	public List<Course> getEnrolledCourse(int uid) {
		// TODO Auto-generated method stub
		return cr.getEnrolledCourses(uid);
	}

	@Override
	public List<Video> getEnrolledCourseVideo(int uid,int cid) {
		// TODO Auto-generated method stub
		List<Video> videos= vr.getVideo(uid,cid);
		System.out.println(videos.size());
		return videos;
	}

}
