package com.itacademy.soccer;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling  //GAME ENGINE - Annotation required for scheduling matches
@SpringBootApplication
public class SoccerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerProjectApplication.class, args);
	}

	// This method is executed after server startup and dependency injection of beans
	// Set correct TimeZone UTC + 2 for correct scheduling time play of matches
	@PostConstruct
    public void initTimeZone(){
        //WARNING: Problems with TimeZone!
		
	    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Madrid")));  
	    //TimeZone.setDefault(TimeZone.getTimeZone("UTC+2"));  
	    System.out.println("Test UTC TimeZone : "+new Date()); //It should be the current time 
    }
	
}
