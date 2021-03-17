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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonInclude
	@Transient
	private int  avgrating;
	
	@JsonInclude
	@Transient
	private int enrollments;
	
	@JsonInclude
	@Transient
	private int totalcomment;
	
	@JsonInclude
	@Transient
	private int videosize;
	
	

	@OneToMany(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId", referencedColumnName = "courseId")
	List<Video> video;
	
	@OneToMany(targetEntity = Like.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
    List<Like> likess;
	
	@OneToMany(targetEntity = Feedback.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
	List<Feedback> feedbacks;
	
	@OneToMany(targetEntity = EnrolledCourses.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
	List<EnrolledCourses> ecourse; 
	
	@ManyToOne(targetEntity = Category.class ,fetch = FetchType.LAZY)
	@JoinColumn(name="categoryId")
	private Category category;


//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "resultID", nullable = false, referencedColumnName = "resultID")
//	private Result result;
	@JsonIgnore
	@JsonProperty(value = "likess")
	public List<Like> getLikess() {
		return likess;
	}

	public void setLikess(List<Like> likess) {
		this.likess = likess;
	}

	public Course(String courseName, String courseDesc, String courseLogo, int coursePrice) {
		super();
		this.courseName = courseName;
		this.courseDesc = courseDesc;
		this.courseLogo = courseLogo;
		this.coursePrice = coursePrice;
	}

	public Course() {
		super();
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

	public int getVideosize() {
		return videosize;
	}

	public void setVideosize(int videosize) {
		this.videosize = videosize;
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

	public Course(String courseName, String courseDesc, String courseLogo, int coursePrice, int likes) {
		super();
		this.courseName = courseName;
		this.courseDesc = courseDesc;
		this.courseLogo = courseLogo;
		this.coursePrice = coursePrice;
		this.likes = 0;
	}
	@JsonIgnore
	@JsonProperty(value = "feedbacks")
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	@JsonIgnore
	@JsonProperty(value = "ecourse")
	public List<EnrolledCourses> getEcourse() {
		return ecourse;
	}

	public void setEcourse(List<EnrolledCourses> ecourse) {
		this.ecourse = ecourse;
	}

	public int getAvgrating() {
		return avgrating;
	}

	public void setAvgrating(int avgrating) {
		this.avgrating = avgrating;
	}

	public int getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(int enrollments) {
		this.enrollments = enrollments;
	}

	public int getTotalcomment() {
		return totalcomment;
	}

	public void setTotalcomment(int totalcomment) {
		this.totalcomment = totalcomment;
	}


	
}
