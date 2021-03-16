package com.example.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
	@Autowired
	ProfileRepo pfr;
	@Autowired
	EnrolledCourseVideoRepo ecvr;
	@Autowired
	CertiRepo ctr;
	@Autowired
	CommentRepo cmtr;
	

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

	@Override
	public boolean lockAccount(int uid) {
		// TODO Auto-generated method stub
		Optional<User> user=ur.findById(uid);
		user.get().setLocked(true);
		ur.save(user.get());
		
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean unlocakAccount(int uid) {
		Optional<User> user=ur.findById(uid);
		user.get().setLocked(false);
		ur.save(user.get());
		
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<User> getLockedAccount() {
		// TODO Auto-generated method stub
		return ur.getLockedUsers();
	}

	@Override
	public boolean generateCompeletionCerti(int uid, int cid) {
		// TODO Auto-generated method stub
		
		Optional<User> user=ur.findById(uid);
		Profile profile=pfr.findByUser(user.get());
		Optional<Course> course = cr.findById(cid);
		System.out.println(profile.getFullName());
		System.out.println(course.get().getCourseName());
		EnrolledCourses ec=ecr.findByUserAndCourse(user.get(), course.get());
		Date enddate = ec.getEndDate();
		System.out.println(ec.getEndDate());
		String pdfname=user.get().getUsername()+course.get().getCourseName();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(pdfname+".pdf"));
			document.open();
			Paragraph right=new Paragraph(Element.ALIGN_RIGHT);
			Paragraph left=new Paragraph(Element.ALIGN_LEFT);
			Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 30, BaseColor.BLACK);
			Chunk chunk = new Chunk("Certificate Of Completion", font);
			Paragraph preface = new Paragraph(chunk); 
			
			preface.setAlignment(Element.ALIGN_CENTER);
			document.add(preface);
			
			
			
			Image img = Image.getInstance(course.get().getCourseLogo());
	
			
			right.add(img);
			
			Font font1 = FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 15, BaseColor.GRAY);
			Font font2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 25, BaseColor.DARK_GRAY);
			Chunk chunk1=new Chunk("This is to Certify that"+"\n",font1);
			Chunk chunk2=new Chunk(profile.getFullName()+"\n",font2);
			Chunk chunk3=new Chunk("has successfully completed "+course.get().getCourseName()+" course on date "+"\n",font1);
			Chunk chunk4=new Chunk("'"+enddate+"'");
		
			PdfPCell leftcell=new PdfPCell();
			leftcell.addElement(chunk1);leftcell.addElement(chunk2);leftcell.addElement(chunk3);leftcell.addElement(chunk4);
			PdfPCell rightcell=new PdfPCell(img);
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
			
			Certificate certi=new Certificate(pdfname+".pdf", course.get(), user.get());
			ctr.save(certi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return false;
	}
	public boolean Enroll(int cid,int uid) {
		// TODO Auto-generated method stub
		Optional<Course> course = cr.findById(cid); 
		Optional<User> u=ur.findById(uid);
		List<Video> videos=vr.findAllByCourse(course.get());
		List<EnrolledCourseVideo> ecvideos=new ArrayList<>();
		for (Video video : videos) {
			EnrolledCourseVideo ecv=new EnrolledCourseVideo(0,false, video, null);
			ecvideos.add(ecv);
			
		}
		
		long millis=System.currentTimeMillis();
		Date date=new java.sql.Date(millis);
		EnrolledCourses ec=new EnrolledCourses( date, null, u.get(), course.get(), ecvideos);
		ecr.save(ec);
		return true;
	}

	@Override
	public boolean nextVideo(int cid, int uid, int vid) {
		// TODO Auto-generated method stub
		Optional<Course> course = cr.findById(cid); 
		Optional<Video> video = vr.findById(vid);
		Optional<User> u=ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(), course.get());
		int ecid=ec.getEcourseId();
		List<EnrolledCourseVideo> ecvs = ecvr.findAllByEc(ec);
		for(int i=0;i<ecvs.size();i++) {
			if(ecvs.get(i).getVideo()==vid) {
				if(i==0) {
					return true;
				}
				if(ecvs.get(i-1).isCompleted()==true) {
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
		Optional<User> u=ur.findById(uid);
		EnrolledCourses ec = ecr.findByUserAndCourse(u.get(), course.get());
		List<EnrolledCourseVideo> ecvs = ec.getEcvideo();
		for(int i=0;i<ecvs.size();i++) {
			if(ecvs.get(i).getVideo()==vid) {
			ecvs.get(i).setCompleted(true);
			if(i==ecvs.size()-1) {
				long millis=System.currentTimeMillis();
				Date date=new java.sql.Date(millis);
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
	public boolean incrementfailed(String username) {
		// TODO Auto-generated method stub
		User user = ur.findByUsername(username);
		if(user!=null) {
			if(user.getFailedattempts()==2) {
				lockAccount(user.getUserId());
			}
			if(user.getFailedattempts()<3) {
				int temp=user.getFailedattempts();
				user.setFailedattempts(++temp);
				ur.save(user);
			}
			
		}
		
		
		return false;
	}

	@Override
	public boolean clearfalied(int uid) {
		unlocakAccount(uid);
		Optional<User> user = ur.findById(uid);
		user.get().setFailedattempts(0);
		ur.save(user.get());
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked(String username) {
		// TODO Auto-generated method stub
		User user = ur.findByUsername(username);
		return user.isLocked();
	}
	
	
	@Override
	public String createUser(User user) {
		Profile p=new Profile();
		User u=new User(user.getUserId(), user.getUsername(), user.getEmail(), new BCryptPasswordEncoder().encode(user.getPassword()), false, false, "user");
		p.setUser(u);
		u.setProfile(p);
		ur.save(u);
		return "successfully created user";
	}

	@Override
	public boolean isActivated(int uid) {
		// TODO Auto-generated method stub
		Optional<User> user = ur.findById(uid);
		return user.get().isActivated();
	}
	@Override
	public Profile createProfile(int userid, Profile profile) {
		Optional<User> user = ur.findUserDetails(userid);
		Profile pp = pfr.findByUser(user.get());
		pp.setBirthdate(profile.getBirthdate());
		pp.setFullName(profile.getFullName());
		pp.setGender(profile.getGender());
		//pp.setUserImage(profile.getUserImage());
		return pfr.save(pp);

	}

	@Override
	public boolean isProfileCreated(int uid) {
		// TODO Auto-generated method stub
		Optional<User> user = ur.findById(uid);
		Profile profile = pfr.findByUser(user.get());
		if(profile.getFullName()!=null)
		return true;
		return false;
	}


	
}
