package com.itacademy.soccer;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling  //GAME ENGINE - Annotation required for scheduling matches
@SpringBootApplication
public class SoccerProjectApplication {
	
	//B-61 Bean for encryption to work
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	public static void main(String[] args) {

		SpringApplication.run(SoccerProjectApplication.class, args);
	}

	// This method is executed after server startup and dependency injection of beans
	// Set correct TimeZone UTC + 2 for correct scheduling time play of matches
	@PostConstruct
    public void initTimeZone(){
		
	    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Madrid")));  
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
	    System.out.println("Test UTC TimeZone : "+new Date()); //It should be the current time 
    }
	
}
