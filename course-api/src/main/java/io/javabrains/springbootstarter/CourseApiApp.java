package io.javabrains.springbootstarter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.javabrains.springbootstarter.Service.SoccerService;

@SpringBootApplication
public class CourseApiApp {

	   
	public static void main(String[] args) {

		SpringApplication.run(CourseApiApp.class,args);

	}
	
	
}
