package com.example.entity;

import java.sql.Date;
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

@Entity
public class EnrolledCourses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ecourseId;
	private Date startDate;
	private Date endDate;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name="courseId")
	private Course course;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;
	
	
	@OneToMany(targetEntity = EnrolledCourseVideo.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="ecourseId", referencedColumnName = "ecourseId")
	List<EnrolledCourseVideo> ecvideo;


	public int getEcourseId() {
		return ecourseId;
	}


	public void setEcourseId(int ecourseId) {
		this.ecourseId = ecourseId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	


	public List<EnrolledCourseVideo> getEcvideo() {
		return ecvideo;
	}


	public void setEcvideo(List<EnrolledCourseVideo> ecvideo) {
		this.ecvideo = ecvideo;
	}


	public EnrolledCourses() {
		super();
	}
	
	
	
}
