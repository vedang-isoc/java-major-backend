package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int videoId;

	public Video() {
		super();
	}

	private String videoName;
	private String videoDesc;
	private String videoPath;

	@JsonInclude
	@Transient
	private String courseName;

	public Video(String videoName, String videoDesc, String videoPath) {
		super();
		this.videoName = videoName;
		this.videoDesc = videoDesc;
		this.videoPath = videoPath;
	}

	protected int getVideoId() {
		return videoId;
	}

	protected void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	protected String getVideoName() {
		return videoName;
	}

	protected void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	protected String getVideoDesc() {
		return videoDesc;
	}

	protected void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	protected String getVideoPath() {
		return videoPath;
	}

	protected void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	protected String getCourseName() {
		return courseName;
	}

	protected void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	
	
}
