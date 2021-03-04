package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Profile;
import com.example.entity.User;
import com.example.repositiories.Categoryrepo;
import com.example.repositiories.ProfileRepo;
import com.example.repositiories.UserRepo;

@SpringBootApplication
public class JavaMajorrG6Application implements CommandLineRunner {

	@Autowired
	UserRepo ur;
	@Autowired
	ProfileRepo pr;
	@Autowired
	Categoryrepo catr;

	public static void main(String[] args) {
		SpringApplication.run(JavaMajorrG6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		Profile p = new Profile();
//		User u = new User("uname", "xyz@gmail.com", "123xyz", true, true, "kjfsgbikrg");
//		p.setUser(u);
//		u.setProfile(p);
//		ur.save(u);
//		Course course = new Course("angular", "learn angular", null, 290, 0);
//		Course course1 = new Course("react", "learn react", null, 123, 0);
//		List<Course> courses = new ArrayList<>();
//		courses.add(course1);
//		courses.add(course);
//		Category category = new Category("frontend", "learn frontend", null, courses);
//		catr.save(category);

		// cr.save(c1);

	}

}
