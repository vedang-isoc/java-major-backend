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

	@OneToMany(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "courseId", referencedColumnName = "courseId")
	List<Video> video;

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

	public Course() {
		super();
	}

}
