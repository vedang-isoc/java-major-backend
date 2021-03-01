package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Profile;
import com.example.entity.User;
import com.example.repositiories.ProfileRepo;
import com.example.repositiories.UserRepo;





@SpringBootApplication
public class JavaMajorrG6Application implements CommandLineRunner {

	
	@Autowired
	UserRepo ur;
	@Autowired
	ProfileRepo pr;
	
	public static void main(String[] args) {
		SpringApplication.run(JavaMajorrG6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Profile p=new Profile();
		User u=new User("uname", "xyz@gmail.com", "123xyz", true, true, "kjfsgbikrg");
		p.setUser(u);
		u.setProfile(p);
		ur.save(u);
		
		
	
		//cr.save(c1);
		
	}

}
