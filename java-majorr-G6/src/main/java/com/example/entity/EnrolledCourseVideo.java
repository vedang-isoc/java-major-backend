package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class EnrolledCourseVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ecvId;
	private int timeSeen;
	private boolean completed;
	
	

	@ManyToOne(targetEntity = Video.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="videoId", referencedColumnName = "videoId")
	private Video video;
	
	@ManyToOne(targetEntity = EnrolledCourses.class, fetch = FetchType.LAZY)
	@JoinColumn(name="ecourseId", referencedColumnName = "ecourseId")
	private EnrolledCourses ec;


	public int getEcvId() {
		return ecvId;
	}


	public void setEcvId(int ecvId) {
		this.ecvId = ecvId;
	}


	public int getTimeSeen() {
		return timeSeen;
	}


	public void setTimeSeen(int timeSeen) {
		this.timeSeen = timeSeen;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	


	public EnrolledCourses getEc() {
		return ec;
	}


	public void setEc(EnrolledCourses ec) {
		this.ec = ec;
	}


	


	public EnrolledCourseVideo() {
		super();
	}


	public Video getVideo() {
		return video;
	}


	public void setVideo(Video video) {
		this.video = video;
	}


	public EnrolledCourseVideo(int timeSeen, boolean completed, Video video, EnrolledCourses ec) {
		super();
		this.timeSeen = timeSeen;
		this.completed = completed;
		this.video = video;
		this.ec = ec;
	}
	
	
	
	//@OneToMany(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "ecvId", referencedColumnName = "ecvId")
	//List<Video> video;
	
	
	
}
