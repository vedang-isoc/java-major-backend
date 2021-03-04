package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int courseId;
	private String courseName;
	private String courseDesc;
	private String courseLogo;
	private int coursePrice;
	private int likes;

	@JsonInclude
	@Transient
	private String categoryName;

	@OneToMany(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId", referencedColumnName = "courseId")
	List<Video> video;
	
	@OneToMany(targetEntity = Comment.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
    List<Comment> comments;
	
	@OneToMany(targetEntity = Feedback.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
    List<Feedback> feedbacks;
	
	@OneToMany(targetEntity = EnrolledCourses.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
    List<EnrolledCourses> enrolledcourses;

//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "resultID", nullable = false, referencedColumnName = "resultID")
//	private Result result;

	public Course(String courseName, String courseDesc, String courseLogo, int coursePrice) {
		super();
		this.courseName = courseName;
		this.courseDesc = courseDesc;
		this.courseLogo = courseLogo;
		this.coursePrice = coursePrice;
	}

	

	public Course(int courseId) {
		super();
		this.courseId = courseId;
	}

	public Course(int courseId, List<Comment> comments) {
		super();
		this.courseId = courseId;
		this.comments = comments;
	}

	public Course(String courseName, String courseDesc, String courseLogo, int coursePrice, int likes) {
		super();
		this.courseName = courseName;
		this.courseDesc = courseDesc;
		this.courseLogo = courseLogo;
		this.coursePrice = coursePrice;
		this.likes = likes;
	}



	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public String getCourseLogo() {
		return courseLogo;
	}

	public void setCourseLogo(String courseLogo) {
		this.courseLogo = courseLogo;
	}

	public int getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(int coursePrice) {
		this.coursePrice = coursePrice;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Video> getVideo() {
		return video;
	}

	public void setVideo(List<Video> video) {
		this.video = video;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public Course() {
		super();
	}
	
	

}
