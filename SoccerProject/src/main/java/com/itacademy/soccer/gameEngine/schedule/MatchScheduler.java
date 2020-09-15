package com.itacademy.soccer.gameEngine.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class MatchScheduler {
	
	    private final TaskScheduler taskScheduler;
	    
	    @Autowired
	    public MatchScheduler(TaskScheduler ts) {
	        this.taskScheduler = ts;
	    }

	    public void schedule(Runnable task,Date date) {
	    
	        taskScheduler.schedule(task, date);
	        
	    }
	    	
}
