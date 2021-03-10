package com.example.service;

import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Certificate;
import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourseVideo;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.Like;
import com.example.entity.Profile;
import com.example.entity.User;
import com.example.entity.Video;
import com.example.repositiories.CertiRepo;
import com.example.repositiories.CommentRepo;
import com.example.repositiories.CourseRepo;
import com.example.repositiories.EnrolledCourseRepo;
import com.example.repositiories.EnrolledCourseVideoRepo;
import com.example.repositiories.FeedbackRepo;
import com.example.repositiories.LikeRepo;
import com.example.repositiories.ProfileRepo;
import com.example.repositiories.UserRepo;
import com.example.repositiories.VideoRepo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

	@Autowired
	VideoRepo vr;

	@Autowired
	LikeRepo lr;

	@Autowired
	EnrolledCourseVideoRepo ecvr;

	@Autowired
	ProfileRepo pfr;
	
	@Autowired
	CertiRepo ctr;

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
	public Feedback addFeedback(int uid, int cid, String feedback, int rating) {
		Optional<User> userD = ur.findById(uid);
		Optional<Course> courseD = cr.findById(cid);
		boolean value = isCourseCompleted(cid, uid);
		System.out.println(value);
//		System.out.println(userD.get().getUserId());
//		System.out.println(courseD.get().getCourseId());
//		System.out.println(fr.findAllByUserAndCourse(userD.get(), courseD.get()).size());
		if (fr.findAllByUserAndCourse(userD.get(), courseD.get()).size() == 0) {
			if (value == true) {
				Optional<Course> c = cr.findById(cid);
				Optional<User> u = ur.findById(uid);
				Feedback fb = new Feedback(feedback, c.get(), u.get(), rating);
				// TODO Auto-generated method stub

				return fr.save(fb);
			}
		} else {
			System.out.println("do not add");
		}
//		if (fr.findAllByUserAndCourse(userD.get(), courseD.get()).size() == 1) {
//			if(value == true) {
//				Optional<Course> c = cr.findById(cid);
//				Optional<User> u = ur.findById(uid);
//				Feedback fb = new Feedback(feedback, c.get(), u.get());
//				// TODO Auto-generated method stub
//
//				return fr.save(fb);
//			}
//		}

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

	@Override
	public List<Course> AllCourse() {
		return setAvgRating(cr.findAll());
	}

	@Override
	public List<Course> setAvgRating(List<Course> courses) {
		for (Course c : courses) {
			float avgrating = 0;
			for (Feedback f : c.getFeedbacks()) {
				avgrating = avgrating + f.getRating();
			}
			int rating = (int) (avgrating / (c.getFeedbacks().size()));
			c.setRating(rating);
		}
		return courses;
	}

	@Override
	public Optional<Course> getCourseById(int courseid) {

		return setAvgRating(cr.findById(courseid));
	}

	@Override
	public Optional<Course> setAvgRating(Optional<Course> c) {

		float avgrating = 0;
		for (Feedback f : c.get().getFeedbacks()) {
			avgrating = avgrating + f.getRating();
		}
		int rating = (int) (avgrating / (c.get().getFeedbacks().size()));
		c.get().setRating(rating);
		return c;
	}

	@Override
	public int feedbackcount(int courseid) {
		Optional<Course> course = cr.findById(courseid);
		return course.get().getFeedbacks().size();
	}

	@Override
	public int commentcount(int courseid) {
		Optional<Course> course = cr.findById(courseid);
		return course.get().getComments().size();
	}

	@Override
	public List<Video> getVideos(int courseId) {
//		System.out.println("inside impl"); 
//		return vr.findAll();
		Optional<Course> course = cr.findById(courseId);
		return vr.findAllByCourse(course.get());
	}

	@Override
	public boolean like(int uid, int cid) {

		Optional<Course> c = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		Like IfLike = lr.findByCourseAndUser(c.get(), u.get());
		if (IfLike == null) {
			Like like = new Like(c.get(), u.get());
			int likes = c.get().getLikes();
			c.get().setLikes(++likes);
			cr.save(c.get());
			lr.save(like);
			return true;
		}

		return false;

	}

	@Override
	public boolean unlike(int uid, int cid) {

		Optional<Course> c = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		Like IfLike = lr.findByCourseAndUser(c.get(), u.get());
		if (IfLike != null) {
			int likes = c.get().getLikes();
			c.get().setLikes(--likes);
			lr.deleteById(IfLike.getLikeId());
			cr.save(c.get());
			return true;
		}
		return false;
//		Optional<Like> IfLike=lr.findById(likeid);
//		if(IfLike.get()!=null) {
//			Optional<Course> c=cr.findById(cid);
//			int likes=c.get().getLikes();c.get().setLikes(--likes);
//			cr.save(c.get());
//			lr.deleteById(likeid);
//			
//			return true;
//			
//		}
//		return false;

	}

	@Override
	public boolean isliked(int cid, int uid) {
		Optional<Course> c = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		Like IfLike = lr.findByCourseAndUser(c.get(), u.get());
		if (IfLike == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean Enroll(int cid, int uid) {
		Optional<Course> course = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		List<Video> videos = vr.findAllByCourse(course.get());
		List<EnrolledCourseVideo> ecvideos = new ArrayList<>();
		for (Video video : videos) {
			EnrolledCourseVideo ecv = new EnrolledCourseVideo(0, false, video, null);
			ecvideos.add(ecv);

		}

		long millis = System.currentTimeMillis();
		Date date = new java.sql.Date(millis);
		EnrolledCourses ec = new EnrolledCourses(date, null, u.get(), course.get(), ecvideos);
		ecr.save(ec);
		return true;
	}

	@Override
	public boolean isenrolled(int cid, int uid) {
		Optional<Course> c = cr.findById(cid);
		Optional<User> u = ur.findById(uid);
		EnrolledCourses Ifenrolled = ecr.findByCourseAndUser(c.get(), u.get());
		if (Ifenrolled == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<Course> getEnrolledCourse(int uid) {
		// TODO Auto-generated method stub
		return cr.getEnrolledCourses(uid);
	}

	@Override
	public List<Video> getEnrolledCourseVideo(int uid, int cid) {
		// TODO Auto-generated method stub
		List<Video> videos = vr.getVideo(uid, cid);
		System.out.println(videos.size());
		return videos;
	}

	@Override
	public boolean nextVideo(int cid, int uid, int vid) {
		// TODO Auto-generated method stub
		Optional<Course> course = cr.findById(cid);
		Optional<Video> video = vr.findById(vid);
		Optional<User> u = ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(), course.get());
		int ecid = ec.getEcourseId();
		List<EnrolledCourseVideo> ecvs = ecvr.findAllByEc(ec);
		for (int i = 0; i < ecvs.size(); i++) {
			if (ecvs.get(i).getVideo() == vid) {
				if (i == 0) {
					return true;
				}
				if (ecvs.get(i - 1).isCompleted() == true) {
					return true;
				}
				return false;
			}

		}
		return false;
	}

	@Override
	public boolean completeVideo(int cid, int uid, int vid) {
		Optional<Course> course = cr.findById(cid);
		Optional<Video> video = vr.findById(vid);
		Optional<User> u = ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(), course.get());
		List<EnrolledCourseVideo> ecvs = ec.getEcvideo();
		for (int i = 0; i < ecvs.size(); i++) {
			if (ecvs.get(i).getVideo() == vid) {
				ecvs.get(i).setCompleted(true);
				if (i == ecvs.size() - 1) {
					long millis = System.currentTimeMillis();
					Date date = new java.sql.Date(millis);
					ec.setEndDate(date);
					ec.setEcvideo(ecvs);
					ecr.save(ec);
					return true;

				}
			}

		}
		ec.setEcvideo(ecvs);
		ecr.save(ec);

		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean generateCompeletionCerti(int uid, int cid) {
		// TODO Auto-generated method stub

		Optional<User> user = ur.findById(uid);
		Profile profile = pfr.findByUser(user.get());
		Optional<Course> course = cr.findById(cid);
		System.out.println(profile.getFullName());
		System.out.println(course.get().getCourseName());
		EnrolledCourses ec = ecr.findByUserAndCourse(user.get(), course.get());
		Date enddate = ec.getEndDate();
		System.out.println(ec.getEndDate());
		String pdfname = user.get().getUsername() + course.get().getCourseName();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(pdfname + ".pdf"));
			document.open();
			Paragraph right = new Paragraph(Element.ALIGN_RIGHT);
			Paragraph left = new Paragraph(Element.ALIGN_LEFT);
			Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 30, BaseColor.BLACK);
			Chunk chunk = new Chunk("Certificate Of Completion", font);
			Paragraph preface = new Paragraph(chunk);
			preface.setAlignment(Element.ALIGN_CENTER);
			document.add(preface);

			Image img = Image.getInstance(course.get().getCourseLogo());

			right.add(img);

			Font font1 = FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 15, BaseColor.GRAY);
			Font font2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 25, BaseColor.DARK_GRAY);
			Chunk chunk1 = new Chunk("This is to Certify that" + "\n", font1);
			Chunk chunk2 = new Chunk(profile.getFullName() + "\n", font2);
			Chunk chunk3 = new Chunk(
					"has successfully completed " + course.get().getCourseName() + " course on date " + "\n", font1);
			Chunk chunk4 = new Chunk("'" + enddate + "'");

			PdfPCell leftcell = new PdfPCell();
			leftcell.addElement(chunk1);
			leftcell.addElement(chunk2);
			leftcell.addElement(chunk3);
			leftcell.addElement(chunk4);
			PdfPCell rightcell = new PdfPCell(img);
			leftcell.setBorder(Rectangle.NO_BORDER);
			rightcell.setBorder(Rectangle.NO_BORDER);
			leftcell.setPaddingTop(100);
			rightcell.setPaddingTop(100);
			leftcell.setVerticalAlignment(5);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.addCell(leftcell);
			table.addCell(rightcell);
			table.setPaddingTop(2000);

			document.add(table);
			Image cybage = Image.getInstance("Cybage-e-learning-logo.png");
			cybage.setAlignment(Element.ALIGN_CENTER);
			cybage.setBackgroundColor(BaseColor.BLUE);
			document.add(cybage);

			document.close();

			Certificate certi = new Certificate(pdfname + ".pdf", course.get(), user.get());
			ctr.save(certi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
